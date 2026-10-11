import type { Config } from "@netlify/functions"
import { getStore } from "@netlify/blobs"
import { autorizado, naoAutorizado } from "../lib/auth.mts"

const CHAVE = "site"
const LIMITE = 1_000_000 // 1 MB de JSON é muito mais que o suficiente

// GET: conteúdo do site (o salvo pelo painel, ou o site.json original).
// PUT: salva o conteúdo novo (precisa da senha).
export default async (req: Request) => {
  const store = getStore({ name: "conteudo", consistency: "strong" })

  if (req.method === "GET") {
    const salvo = await store.get(CHAVE, { type: "json" })
    if (salvo) return Response.json(salvo, { headers: { "cache-control": "no-store" } })
    const original = await fetch(new URL("/site.json", req.url))
    return new Response(original.body, {
      status: original.status,
      headers: { "content-type": "application/json", "cache-control": "no-store" },
    })
  }

  if (!autorizado(req)) return naoAutorizado()

  const texto = await req.text()
  if (texto.length > LIMITE) return Response.json({ erro: "Conteúdo grande demais." }, { status: 413 })
  let dados: any
  try { dados = JSON.parse(texto) } catch { return Response.json({ erro: "Conteúdo inválido." }, { status: 400 }) }
  if (!dados || typeof dados !== "object" || !Array.isArray(dados.produtos)) {
    return Response.json({ erro: "Conteúdo inválido: faltou a lista de produtos." }, { status: 400 })
  }
  dados.atualizadoEm = new Date().toISOString()
  await store.setJSON(CHAVE, dados)
  return Response.json({ ok: true, atualizadoEm: dados.atualizadoEm })
}

export const config: Config = { path: "/api/site", method: ["GET", "PUT"] }

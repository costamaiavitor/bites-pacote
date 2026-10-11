import type { Config, Context } from "@netlify/functions"
import { getStore } from "@netlify/blobs"
import { randomUUID } from "node:crypto"
import { autorizado, naoAutorizado } from "../lib/auth.mts"

const TIPOS: Record<string, string> = { "image/jpeg": "jpg", "image/png": "png", "image/webp": "webp" }
const MAXIMO = 4 * 1024 * 1024

// POST /api/foto: recebe a foto (o painel já reduz o tamanho) e devolve o endereço.
// GET  /api/foto/:chave: entrega a foto para o site.
export default async (req: Request, context: Context) => {
  const store = getStore("fotos")

  if (req.method === "GET") {
    const chave = context.params.chave
    if (!chave || !/^[a-f0-9-]+\.(jpg|png|webp)$/.test(chave)) return new Response("Foto não encontrada", { status: 404 })
    const foto = await store.getWithMetadata(chave, { type: "arrayBuffer" })
    if (!foto) return new Response("Foto não encontrada", { status: 404 })
    return new Response(foto.data, {
      headers: {
        "content-type": String(foto.metadata?.tipo || "image/jpeg"),
        "cache-control": "public, max-age=31536000, immutable",
      },
    })
  }

  if (!autorizado(req)) return naoAutorizado()
  const tipo = (req.headers.get("content-type") || "").split(";")[0].trim()
  const ext = TIPOS[tipo]
  if (!ext) return Response.json({ erro: "Envie uma foto em JPG, PNG ou WEBP." }, { status: 415 })
  const corpo = await req.arrayBuffer()
  if (corpo.byteLength === 0) return Response.json({ erro: "A foto chegou vazia." }, { status: 400 })
  if (corpo.byteLength > MAXIMO) return Response.json({ erro: "Foto grande demais (máximo 4 MB)." }, { status: 413 })
  const chave = `${randomUUID()}.${ext}`
  await store.set(chave, corpo, { metadata: { tipo } })
  return Response.json({ url: `/api/foto/${chave}` })
}

export const config: Config = { path: ["/api/foto", "/api/foto/:chave"], method: ["GET", "POST"] }

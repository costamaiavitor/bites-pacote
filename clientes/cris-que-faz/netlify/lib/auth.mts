import { timingSafeEqual } from "node:crypto"

// Confere a senha do painel enviada no cabeçalho x-senha.
export function autorizado(req: Request): boolean {
  const esperada = Netlify.env.get("ADMIN_SENHA") || ""
  const enviada = req.headers.get("x-senha") || ""
  if (!esperada || !enviada) return false
  const a = Buffer.from(esperada), b = Buffer.from(enviada)
  return a.length === b.length && timingSafeEqual(a, b)
}

export const naoAutorizado = () =>
  Response.json({ erro: "Senha incorreta." }, { status: 401 })

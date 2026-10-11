import type { Config } from "@netlify/functions"
import { autorizado, naoAutorizado } from "../lib/auth.mts"

// Só confirma se a senha do painel está certa.
export default async (req: Request) => {
  if (!autorizado(req)) return naoAutorizado()
  return Response.json({ ok: true })
}

export const config: Config = {
  path: "/api/entrar",
  method: "POST",
  rateLimit: { windowLimit: 10, windowSize: 60, aggregateBy: ["ip", "domain"] },
}

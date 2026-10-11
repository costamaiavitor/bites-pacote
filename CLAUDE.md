# Rivi: manual de operação

Este repositório é a base da **Rivi**, agência de sites de Rian e Vitor (Fortaleza, CE).
Claude cuida da estratégia, prospecção, conteúdo e dos sites; Rian e Vitor são os sócios
que falam com os clientes, postam no Instagram, fecham negócio e recebem.
Leia este arquivo inteiro antes de qualquer tarefa.

## A empresa
- Nome provisório: Rivi (Ri de Rian + Vi de Vitor). Renda extra, os dois fazem de tudo.
- Foco: **sites** para pequenos negócios. Tráfego pago só como extra, se o cliente pedir.
- Atende Fortaleza presencialmente e outras cidades online.
- Orçamento de ferramentas: só a assinatura do Claude e do Lovable. Hospedagem grátis no Netlify.
- Ainda sem CNPJ. Preços ainda não definidos: o site da Rivi diz "valor fechado depois de uma conversa".

## Onde está cada coisa
| O quê | Onde |
|---|---|
| Site da Rivi | `rivi/` → https://rivi-sites.netlify.app (Netlify `rivi-sites`). O número de WhatsApp ainda está vazio (`const WHATSAPP` em `rivi/index.html`). |
| Bites (portfólio, projeto conceito) | raiz do repo; publicado em `rivi-sites.netlify.app/bites/` |
| Prévia Carneiro do Ordones | `previas/ordones/` → https://ordones-previa.netlify.app (lead quente: contato repassado ao administrador) |
| Cris que faz (cliente, mãe do Vitor) | `clientes/cris-que-faz/` → https://cris-que-faz-painel.netlify.app, painel em `/admin` (senha na variável `ADMIN_SENHA` do Netlify, nunca no código). O endereço antigo cris-que-faz.netlify.app ainda é a versão anterior, ligada ao repo `costamaiavitor/crisquefaz`. |
| Modelos prontos | GitHub Pages de `costamaiavitor`: brabo-barber, barbearia-mestre-ze (barbearia), rabo-abanando, bicho-cheiroso (pet shop), espelho-meu (salão), nanquim-tattoo, agulha-brava (tatuagem), glace-confeitaria, mel-e-cacau (confeitaria), dona-nena, flor-de-caju, varanda-do-sertao (restaurante), mandacaru/jaguaribe/luzeiro-energia (energia solar), lousa-prof-rafa, caderno-exatas (professores) |
| Leads | `prospeccao/leads-fortaleza.csv` (planilha com status), `prospeccao/mensagens.md` (mensagem de cada um), `prospeccao/leads-whatsapp.html` → https://rivi-leads-c99be3.netlify.app (lista dividida Rian/Vitor) |
| Skill de design | `.claude/skills/frontend-design/` (usar em todo site novo) |

## Como escrevemos com clientes
- Mensagens **sem emoji**, minúsculas, frases curtas, jeito de gente digitando no WhatsApp. Nada que pareça IA.
- Ser honesto desde a primeira frase: "eu e meu sócio fazemos sites". Nunca fingir ser cliente.
- Estrutura: elogio real e específico, uma pergunta simples, quem somos, oferta de prévia sem compromisso.
- Fortaleza: "aqui em fortaleza". Outras cidades: "atendemos online".
- Mandar de 5 a 8 por dia, um por um, nunca em massa.

## Como achar leads
- Não há acesso ao Google Maps nem ao Overpass daqui. O que funciona: matérias de jornal (O Povo, Diário do Nordeste),
  e os diretórios `barbeariaspertodemim.com` e `petshoppertodemim.com` (pedem cabeçalho de navegador; ler com pausa).
- Filtrar: celular (85 9...), nome de negócio (não pessoa física), sem site (testar domínio provável).
- Os sócios podem colar listas do Google Maps no chat para Claude qualificar.

## Sites
- Visual próprio para cada cliente (seguir a skill frontend-design: nada de visual genérico de IA).
- Fotos de pessoas e de "resultados" sempre reais; IA só para ambientação.
- Prévias levam aviso de "prévia feita pela Rivi" e `noindex`.
- Publicação: Netlify via conector (criar projeto, `deploy-site`, rodar o comando `npx @netlify/mcp` na pasta).

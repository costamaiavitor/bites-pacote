/* ============================================================
   Cris que faz — interações do site
   ============================================================ */
(function () {
  "use strict";
  function init(CONFIG){
  const $ = (s, c = document) => c.querySelector(s);
  const $$ = (s, c = document) => Array.from(c.querySelectorAll(s));
  let io; // observer de animações (declarado no topo p/ evitar erro de ordem)

  /* ---------- Ícones ---------- */
  const WA_ICON =
    '<svg viewBox="0 0 24 24" fill="currentColor" aria-hidden="true"><path d="M.06 24l1.69-6.16a11.87 11.87 0 01-1.6-5.95C.16 5.34 5.5 0 12.06 0a11.8 11.8 0 018.4 3.49 11.8 11.8 0 013.48 8.4c0 6.55-5.34 11.89-11.9 11.89a11.9 11.9 0 01-5.68-1.45L.06 24zm6.6-3.8c1.68.99 3.28 1.58 5.4 1.58 5.45 0 9.89-4.43 9.89-9.88 0-5.46-4.43-9.89-9.88-9.89C6.6 1.99 2.16 6.42 2.16 11.9c0 2.22.65 3.88 1.74 5.62l-1 3.66 3.76-.98zm11.36-5.29c-.07-.12-.27-.2-.56-.34-.3-.15-1.76-.87-2.03-.97-.27-.1-.47-.15-.66.15-.2.3-.76.97-.94 1.17-.17.2-.34.22-.64.07-.3-.15-1.26-.46-2.4-1.48-.88-.79-1.48-1.76-1.65-2.06-.17-.3-.02-.46.13-.61.13-.13.3-.34.45-.51.15-.17.2-.3.3-.5.1-.2.05-.37-.02-.52-.08-.15-.66-1.6-.9-2.18-.24-.58-.48-.5-.66-.5l-.56-.01c-.2 0-.52.07-.79.37-.27.3-1.04 1.02-1.04 2.48 0 1.46 1.06 2.88 1.21 3.08.15.2 2.1 3.2 5.08 4.49.71.3 1.26.49 1.69.62.71.23 1.36.2 1.87.12.57-.08 1.76-.72 2-1.41.25-.69.25-1.28.17-1.41z"/></svg>';
  const HEART_ICON =
    '<svg viewBox="0 0 24 24" fill="currentColor" aria-hidden="true"><path d="M12 21s-7.5-4.9-10-9.2C.4 8.9 1.6 5.5 4.7 4.8 6.8 4.3 8.7 5.3 12 8c3.3-2.7 5.2-3.7 7.3-3.2 3.1.7 4.3 4.1 2.7 7C19.5 16.1 12 21 12 21z"/></svg>';

  /* ---------- Utilidades ---------- */
  const waLink = (msg) =>
    "https://wa.me/" + CONFIG.whatsapp + "?text=" + encodeURIComponent(msg);
  const brl = (n) =>
    n.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

  /* ---------- Cabeçalho: estado ao rolar ---------- */
  const header = $(".site-header");
  if (header) {
    const onScroll = () =>
      header.classList.toggle("scrolled", window.scrollY > 40);
    onScroll();
    window.addEventListener("scroll", onScroll, { passive: true });
  }

  /* ---------- Menu mobile ---------- */
  const toggle = $(".nav-toggle");
  const links = $(".nav-links");
  const backdrop = $(".nav-backdrop");
  if (toggle && links) {
    const close = () => {
      toggle.classList.remove("open");
      links.classList.remove("open");
      backdrop && backdrop.classList.remove("show");
      document.body.style.overflow = "";
    };
    toggle.addEventListener("click", () => {
      const open = links.classList.toggle("open");
      toggle.classList.toggle("open", open);
      backdrop && backdrop.classList.toggle("show", open);
      document.body.style.overflow = open ? "hidden" : "";
    });
    backdrop && backdrop.addEventListener("click", close);
    $$(".nav-links a").forEach((a) => a.addEventListener("click", close));
  }

  /* ---------- Card de produto ---------- */
  function productCard(p0) {
    const e = (t) => String(t == null ? "" : t).replace(/[&<>"']/g, (c) => ({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
    const p = Object.assign({}, p0, { nome: e(p0.nome), categoria: e(p0.categoria), descricao: e(p0.descricao), tag: e(p0.tag), imagem: e(p0.imagem), preco: Number(p0.preco) || 0 });
    const msg =
      "Olá! 🌼 Tenho interesse no *" +
      p0.nome +
      "* (" +
      brl(p.preco) +
      "). Pode me dar mais informações?";
    const tag = p.tag ? '<span class="card-tag">' + p.tag + "</span>" : "";
    return (
      '<article class="card reveal">' +
      '<div class="card-media media-' + (p.cor || "almond") + (p.fit==="cover"?" fit-cover":"") + '">' +
      tag +
      '<button class="card-fav" aria-label="Favoritar">' + HEART_ICON + "</button>" +
      '<img src="' + p.imagem + '" alt="' + p.nome + '" loading="lazy">' +
      "</div>" +
      '<div class="card-body">' +
      '<span class="card-cat">' + p.categoria + "</span>" +
      '<h3 class="card-title">' + p.nome + "</h3>" +
      '<p class="card-desc">' + p.descricao + "</p>" +
      '<div class="card-foot">' +
      '<span class="card-price">' + brl(p.preco) + "</span>" +
      '<a class="btn btn-wa" target="_blank" rel="noopener" href="' + waLink(msg) + '">' +
      WA_ICON + " Pedir</a>" +
      "</div></div></article>"
    );
  }

  /* ---------- Foto da capa (topo) ---------- */
  var heroImg = document.getElementById('hero-img');
  if (heroImg && CONFIG.capa) {
    heroImg.src = CONFIG.capa;
    heroImg.style.cssText = 'position:relative;left:auto;bottom:auto;transform:none;height:auto;width:100%;max-width:440px;max-height:90%;object-fit:cover;border-radius:24px;box-shadow:0 30px 60px -25px rgba(0,0,0,.55);margin:0 auto;display:block;filter:none';
    var _disc = document.querySelector('.hero-art .disc'); if (_disc) _disc.style.display='none';
    var _art = heroImg.closest('.hero-art'); if (_art) { _art.style.display='flex'; _art.style.alignItems='center'; _art.style.justifyContent='center'; }
  }

  /* ---------- Início: destaques ---------- */
  const featured = $("#featured-grid");
  if (featured) {
    const list = CONFIG.produtos.filter((p) => p.destaque).slice(0, 6);
    featured.innerHTML = list.map(productCard).join("");
  }

  /* ---------- Loja: catálogo + filtros ---------- */
  const shop = $("#shop-grid");
  if (shop) {
    const cats = ["Todos", ...new Set(CONFIG.produtos.map((p) => p.categoria))];
    const filtersBox = $("#shop-filters");
    if (filtersBox) {
      filtersBox.innerHTML = cats
        .map((c, i) =>
          '<button class="filter' + (i === 0 ? " active" : "") +
          '" data-cat="' + c + '">' + c + "</button>")
        .join("");
    }
    const draw = (cat) => {
      const list =
        cat === "Todos"
          ? CONFIG.produtos
          : CONFIG.produtos.filter((p) => p.categoria === cat);
      shop.innerHTML = list.map(productCard).join("");
      revealObserve();
    };
    draw("Todos");
    if (filtersBox) {
      filtersBox.addEventListener("click", (e) => {
        const b = e.target.closest(".filter");
        if (!b) return;
        $$(".filter", filtersBox).forEach((x) => x.classList.remove("active"));
        b.classList.add("active");
        draw(b.dataset.cat);
      });
    }
  }

  /* ---------- Preenche links de contato ---------- */
  $$("[data-wa]").forEach((el) => {
    const msg =
      el.getAttribute("data-wa") ||
      "Olá! 🌼 Vim pelo site da Cris que faz e gostaria de fazer um pedido.";
    el.setAttribute("href", waLink(msg));
    el.setAttribute("target", "_blank");
    el.setAttribute("rel", "noopener");
  });
  $$("[data-contact='instagram']").forEach((el) => {
    el.href = CONFIG.instagram;
    if (el.dataset.fill) el.textContent = CONFIG.instagramHandle;
  });
  $$("[data-contact='email']").forEach((el) => {
    el.href = "mailto:" + CONFIG.email;
    if (el.dataset.fill) el.textContent = CONFIG.email;
  });
  $$("[data-contact='wa-text']").forEach((el) => {
    el.textContent = "+" + CONFIG.whatsapp.replace(/(\d{2})(\d{2})(\d{4,5})(\d{4})/, "$1 ($2) $3-$4");
  });
  $$("[data-fill='cidade']").forEach((el) => (el.textContent = CONFIG.cidade));
  $$("[data-fill='horario']").forEach((el) => (el.textContent = CONFIG.horario));

  /* ---------- FAQ ---------- */
  $$(".faq-q").forEach((q) => {
    q.addEventListener("click", () => {
      const item = q.closest(".faq-item");
      const a = $(".faq-a", item);
      const open = item.classList.toggle("open");
      a.style.maxHeight = open ? a.scrollHeight + "px" : 0;
    });
  });

  /* ---------- Formulário de contato → WhatsApp ---------- */
  const form = $("#contact-form");
  if (form) {
    form.addEventListener("submit", (e) => {
      e.preventDefault();
      const f = new FormData(form);
      const msg =
        "Olá! 🌼 Meu nome é *" + (f.get("nome") || "") + "*.\n" +
        "Assunto: " + (f.get("assunto") || "Contato") + "\n\n" +
        (f.get("mensagem") || "");
      window.open(waLink(msg), "_blank");
    });
  }

  /* ---------- Animações reveal ---------- */
  function revealObserve() {
    if (!("IntersectionObserver" in window)) {
      $$(".reveal").forEach((el) => el.classList.add("in"));
      return;
    }
    io =
      io ||
      new IntersectionObserver(
        (entries) => {
          entries.forEach((en) => {
            if (en.isIntersecting) {
              en.target.classList.add("in");
              io.unobserve(en.target);
            }
          });
        },
        { threshold: 0.12, rootMargin: "0px 0px -40px 0px" }
      );
    $$(".reveal:not(.in)").forEach((el) => io.observe(el));
  }
  revealObserve();

  /* ---------- Ano no rodapé ---------- */
  $$("[data-year]").forEach((el) => (el.textContent = new Date().getFullYear()));
  }
  /* ---------- Textos e fotos editados no painel ---------- */
  function esc(t){ return String(t).replace(/[&<>"']/g, function(c){ return {"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;","'":"&#39;"}[c]; }); }
  function formatar(t){
    return esc(t).replace(/\*\*(.+?)\*\*/g,"<strong>$1</strong>").replace(/\*(.+?)\*/g,"<em>$1</em>").replace(/\n/g,"<br>");
  }
  function aplicarEdicoes(CONFIG){
    var textos = CONFIG.textos || {}, imagens = CONFIG.imagens || {};
    document.querySelectorAll("[data-edit]").forEach(function(el){
      var v = textos[el.getAttribute("data-edit")];
      if (typeof v === "string" && v.trim() !== "") el.innerHTML = formatar(v);
    });
    document.querySelectorAll("[data-edit-img]").forEach(function(el){
      var v = imagens[el.getAttribute("data-edit-img")];
      if (v) el.src = v;
    });
  }
  function liberar(){ document.documentElement.classList.remove("carregando"); }
  setTimeout(liberar, 1500);

  function carregar(url){ return fetch(url,{cache:'no-store'}).then(function(r){ if(!r.ok) throw new Error(r.status); return r.json(); }); }
  carregar('/api/site')
    .catch(function(){ return carregar('/site.json'); })
    .then(function(CONFIG){ aplicarEdicoes(CONFIG); liberar(); init(CONFIG); })
    .catch(function(e){ liberar(); console.error('Falha ao carregar dados do site:',e); });
})();

(() => {
  function initNavTabs() {
    const list = document.querySelector("[data-nav-tabs]");
    if (!list) return;

    list.addEventListener("click", (e) => {
      const btn = e.target && e.target.closest ? e.target.closest("[data-nav-tab]") : null;
      if (!btn) return;
      const li = btn.closest(".nav__item");
      if (!li) return;

      list.querySelectorAll(".nav__item").forEach((el) => el.classList.remove("is-active"));
      li.classList.add("is-active");
    });
  }

  function initGenreTabs() {
    const root = document.querySelector("[data-genre-tabs]");
    if (!root) return;

    root.addEventListener("click", (e) => {
      const btn = e.target && e.target.closest ? e.target.closest(".genre-tabs__btn") : null;
      if (!btn) return;
      root.querySelectorAll(".genre-tabs__btn").forEach((el) => el.classList.remove("is-active"));
      btn.classList.add("is-active");
    });
  }

  function initHeroSlider() {
    const root = document.querySelector("[data-hero]");
    if (!root) return;

    const track = root.querySelector("[data-hero-track]");
    const slides = Array.from(root.querySelectorAll("[data-hero-slide]"));
    const dots = Array.from(root.querySelectorAll("[data-hero-dot]"));
    const prevBtn = root.querySelector("[data-hero-prev]");
    const nextBtn = root.querySelector("[data-hero-next]");

    if (!track || slides.length === 0) return;

    let index = 0;
    let timer = null;
    const intervalMs = 4000;

    function setActive(i) {
      const max = slides.length;
      index = ((i % max) + max) % max;
      track.style.transform = `translateX(-${index * 100}%)`;

      dots.forEach((d, di) => d.classList.toggle("is-active", di === index));
      slides.forEach((s, si) => s.setAttribute("aria-hidden", si === index ? "false" : "true"));
    }

    function start() {
      stop();
      timer = window.setInterval(() => setActive(index + 1), intervalMs);
    }

    function stop() {
      if (timer !== null) {
        window.clearInterval(timer);
        timer = null;
      }
    }

    if (prevBtn) prevBtn.addEventListener("click", () => setActive(index - 1));
    if (nextBtn) nextBtn.addEventListener("click", () => setActive(index + 1));
    dots.forEach((dot, di) => dot.addEventListener("click", () => setActive(di)));

    root.addEventListener("mouseenter", stop);
    root.addEventListener("mouseleave", start);

    setActive(0);
    start();
  }

  document.addEventListener("DOMContentLoaded", () => {
    initNavTabs();
    initGenreTabs();
    initHeroSlider();
  });
})();



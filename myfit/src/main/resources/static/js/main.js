// Scroll reveal
const reveals = document.querySelectorAll('.reveal');
const observer = new IntersectionObserver(
    entries => {
        entries.forEach(e => {
            if (e.isIntersecting) {
                e.target.classList.add('visible');
                observer.unobserve(e.target);
            }
        });
    },
    { threshold: 0.18 }
);

reveals.forEach(el => observer.observe(el));

// Simple stats counter
document.querySelectorAll('.stat-number').forEach(el => {
    const target = parseFloat(el.dataset.target);
    if (!target) return;

    let current = 0;
    const steps = 30;
    const delta = target / steps;

    const tick = () => {
        current += delta;
        if (current >= target) {
            el.textContent = target.toString();
        } else {
            el.textContent = current.toFixed(target % 1 !== 0 ? 1 : 0);
            requestAnimationFrame(tick);
        }
    };

    requestAnimationFrame(tick);
});

// Smooth scroll for navbar links
document.querySelectorAll('a[href^="#"]').forEach(link => {
    link.addEventListener('click', e => {
        const id = link.getAttribute('href').slice(1);
        const section = document.getElementById(id);
        if (!section) return;

        e.preventDefault();
        window.scrollTo({
            top: section.offsetTop - 80,
            behavior: 'smooth'
        });
    });
});
function openAddTrainerModal(e) {
    if (e) e.preventDefault();
    const modal = document.getElementById('add-trainer-modal');
    if (!modal) return;
    modal.classList.add('open');
    document.body.classList.add('no-scroll');
}

function closeAddTrainerModal() {
    const modal = document.getElementById('add-trainer-modal');
    if (!modal) return;
    modal.classList.remove('open');
    document.body.classList.remove('no-scroll');

    const form = document.getElementById('add-trainer-form');
    if (form) {
        form.reset(); // по желание – чисти полетата
    }
}
document.addEventListener("DOMContentLoaded", function () {
    const openBtn = document.getElementById("openAddTrainer");
    const closeBtn = document.getElementById("closeAddTrainer");
    const modal = document.getElementById("addTrainerModal");

    if (openBtn && modal) {
        openBtn.addEventListener("click", () => {
            modal.style.display = "flex"; // показваме overlay-а
        });
    }

    if (closeBtn && modal) {
        closeBtn.addEventListener("click", () => {
            modal.style.display = "none";
        });
    }

    // затваряне при клик върху тъмния фон
    if (modal) {
        modal.addEventListener("click", (e) => {
            if (e.target === modal) {
                modal.style.display = "none";
            }
        });
    }
});

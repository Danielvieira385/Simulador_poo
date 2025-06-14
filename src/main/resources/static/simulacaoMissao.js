document.addEventListener("DOMContentLoaded", function () {
    let duracaoTexto = document.querySelector('[th\\:text="${duracao}"]')?.textContent?.trim() || "5s";
    let duracaoSegundos = parseInt(duracaoTexto.replace(/[^\d]/g, "")) || 5;

    let barra = document.getElementById("barraProgresso");
    let resultado = document.getElementById("resultado");

    let tempoTotal = duracaoSegundos * 1000;
    let intervalo = 100;
    let incremento = (intervalo / tempoTotal) * 100;

    let progresso = 0;
    let animacao = setInterval(() => {
        progresso += incremento;
        if (progresso >= 100) {
            progresso = 100;
            clearInterval(animacao);
            resultado.style.display = "block";
        }
        barra.style.width = progresso + "%";
        barra.textContent = Math.floor(progresso) + "%";
    }, intervalo);
});

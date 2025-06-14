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
            barra.style.width = progresso + "%";
            barra.textContent = Math.floor(progresso) + "%";
            resultado.style.display = "block";
            iniciarSimulacaoCombate();  // ⬅️ Começa a batalha aqui
        } else {
            barra.style.width = progresso + "%";
            barra.textContent = Math.floor(progresso) + "%";
        }
    }, intervalo);
});

// 🧠 Essa função só é chamada ao terminar a barra
function iniciarSimulacaoCombate() {
    const combateLog = typeof combateLogData !== 'undefined' ? combateLogData : [];
    const resultadoDiv = document.getElementById("simulacaoCombate");

    let index = 0;
    let vidaPersonagem = 100;
    let vidaAdversario = 100;

    // Criação das barras de vida
    const vidaDiv = document.createElement("div");
    vidaDiv.innerHTML = `
        <p><strong>Vida Personagem:</strong> <progress id="vidaPersonagem" value="100" max="100"></progress></p>
        <p><strong>Vida Adversário:</strong> <progress id="vidaAdversario" value="100" max="100"></progress></p>
    `;
    resultadoDiv.appendChild(vidaDiv);

    const logDiv = document.createElement("div");
    resultadoDiv.appendChild(logDiv);

    function mostrarProximoTurno() {
        if (index < combateLog.length) {
            const msg = combateLog[index];
            const p = document.createElement("p");
            p.textContent = msg;
            logDiv.appendChild(p);

            if (msg.includes("Vida do adversário")) {
                const match = msg.match(/Vida do adversário: (-?\d+)/);
                if (match) {
                    vidaAdversario = Math.max(0, parseInt(match[1]));
                    document.getElementById("vidaAdversario").value = vidaAdversario;
                }
            }
            if (msg.includes("Vida do personagem")) {
                const match = msg.match(/Vida do personagem: (-?\d+)/);
                if (match) {
                    vidaPersonagem = Math.max(0, parseInt(match[1]));
                    document.getElementById("vidaPersonagem").value = vidaPersonagem;
                }
            }
            index++;
            setTimeout(mostrarProximoTurno, 1500);
        } else {
            const fim = document.createElement("p");
            fim.textContent = "Combate terminado.";
            fim.style.fontWeight = "bold";
            resultadoDiv.appendChild(fim);

            const primeiroAtacanteElem = document.getElementById("primeiroAtacante");
            const vencedorElem = document.getElementById("vencedor");

            if (primeiroAtacanteElem) {
                const p1 = document.createElement("p");
                p1.textContent = "Primeiro Atacante: " + (primeiroAtacanteElem.textContent || primeiroAtacanteElem.value);
                resultadoDiv.appendChild(p1);
            }

            if (vencedorElem) {
                const p2 = document.createElement("p");
                p2.textContent = "Vencedor: " + (vencedorElem.textContent || vencedorElem.value);
                resultadoDiv.appendChild(p2);
            }
        }
    }

    mostrarProximoTurno();
}

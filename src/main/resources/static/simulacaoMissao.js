document.addEventListener("DOMContentLoaded", () => {
    // extrair segundos da string duracao, ex: "5s" -> 5
    let duracaoSegundos = parseInt(duracao.replace(/[^\d]/g, "")) || 5;

    let barra = document.getElementById("barraProgresso");
    let resultadoDiv = document.getElementById("simulacaoCombate");

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
            resultadoDiv.style.display = "block";
            iniciarSimulacaoCombate();  // Começa a simulação do combate
        } else {
            barra.style.width = progresso + "%";
            barra.textContent = Math.floor(progresso) + "%";
        }
    }, intervalo);
});

function iniciarSimulacaoCombate() {
    const combateLog = combateLogData || [];
    const resultadoDiv = document.getElementById("simulacaoCombate");

    resultadoDiv.innerHTML = ""; // limpa conteúdo

    let vidaPersonagem = 100;
    let vidaAdversario = 100;

    // Barras de vida
    const vidaDiv = document.createElement("div");
    vidaDiv.innerHTML = `
        <p><strong>Vida Personagem:</strong> <progress id="vidaPersonagem" value="100" max="100"></progress></p>
        <p><strong>Vida Adversário:</strong> <progress id="vidaAdversario" value="100" max="100"></progress></p>
    `;
    resultadoDiv.appendChild(vidaDiv);

    const logDiv = document.createElement("div");
    resultadoDiv.appendChild(logDiv);

    let index = 0;
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

            // Mostrar info primeiro atacante e vencedor
            const p1 = document.createElement("p");
            p1.textContent = "Primeiro Atacante: " + primeiroAtacante;
            resultadoDiv.appendChild(p1);

            const p2 = document.createElement("p");
            p2.textContent = "Vencedor: " + vencedor;
            resultadoDiv.appendChild(p2);

            // Botão para voltar à Taberna
            const form = document.createElement("form");
            form.method = "post";
            form.action = "/taberna";
            form.innerHTML = `
                <input type="hidden" name="personagemIdTaberna" value="${document.querySelector('[name=\"personagemIdTaberna\"]').value}">
                <button type="submit" style="margin-top:20px;">Voltar para a Taberna</button>
            `;
            resultadoDiv.appendChild(form);
        }
    }

    mostrarProximoTurno();
}

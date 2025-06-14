document.addEventListener("DOMContentLoaded", () => {
    const combateLog = combateLogData;  // `combateLogData` declarado no Thymeleaf inline JS

    const resultadoDiv = document.getElementById("simulacaoCombate");
    let index = 0;
    let vidaPersonagem = 100;
    let vidaAdversario = 100;

    // Criar barras de vida
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

            // Atualiza barras de vida com base nas mensagens
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

            // Mostrar primeiro atacante e vencedor
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
});

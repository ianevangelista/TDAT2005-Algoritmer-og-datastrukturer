import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.lang.Math;

public class EdmondsKarp {
	static int N; // antall noder
	static int K; // antall kanter
	static Node[] node;

	public static void main(String[] args) {
		ny_graf("flytgraf3");

		int kilde = 0; // Kilde
		int sluk = 1; // Sluk
		int maxFlow = 0;
		ArrayList<Integer> rekkefølge = new ArrayList<Integer>();
		System.out.println("Økning   :   Flytøkende vei");

		while (true) {
			// Forelder array som brukes for å lagre path
			// forelder[i] lagrer edge brukt til å nå node i
			Kant[] forelder = new Kant[N];

			ArrayList<Node> kø = new ArrayList<Node>();
			kø.add(node[kilde]);

			// BFS for å finne korteste vei
			while (!kø.isEmpty()) {
				Node currentNode = kø.remove(0);

				// Sjekker om kanten ikke har blitt besøkt enda
				// Peker til kilden og om det er mulig å sende flow gjennom
				for (Kant kant : currentNode.kanter) {
					if (forelder[kant.til] == null && kant.til != kilde && kant.kapasitet > kant.flow) {
						forelder[kant.til] = kant;
						kø.add(node[kant.til]);
					}
				}
			}

			// Hvis sluket ikke ble nådd, ble ingen vei funnet fra kilde til sluket
			// Algortimen slutter og printer ut max-flow
			if (forelder[sluk] == null) {
				break;
			}

			// Hvis sluket ble nådd dytter man mer flow gjennom pathen
			int pushFlow = Integer.MAX_VALUE;

			// Finner maksimum flow som kan gå igjennom hele pathen ved å finne det minste
			// gjenstående flow av hver kant i pathen
			for (Kant e = forelder[sluk]; e != null; e = forelder[e.fra]) {
				pushFlow = Math.min(pushFlow, e.kapasitet - e.flow);
			}

			// Legger til flow-verdier og subtraherer fra revers-flow-verdier i pathen
			for (Kant e = forelder[sluk]; e != null; e = forelder[e.fra]) {
				rekkefølge.add(e.til);
				e.flow += pushFlow;
				e.revers.flow -= pushFlow;
			}

			System.out.print(pushFlow + "            " + kilde + " ");
			for (int i = rekkefølge.size() - 1; i >= 0; i--) {
				System.out.print(rekkefølge.get(i) + " ");
			}
			System.out.println("");
			rekkefølge.clear();
			maxFlow += pushFlow;
		}
		System.out.println("Maksimal flyt ble " + maxFlow);
	}

	public static void ny_graf(String filnavn) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filnavn));
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			// Lager en Node-liste med N-elementer
			node = new Node[N];

			// Legger inn noder i lista
			for (int i = 0; i < N; i++) {
				node[i] = new Node();
			}

			K = Integer.parseInt(st.nextToken());
			// Leser inn alle K-linjer
			for (int j = 0; j < K; j++) {
				st = new StringTokenizer(br.readLine());
				int fra = Integer.parseInt(st.nextToken());
				int til = Integer.parseInt(st.nextToken());
				int kapasitet = Integer.parseInt(st.nextToken());

				Kant a = new Kant(fra, til, 0, kapasitet);
				Kant b = new Kant(til, fra, 0, 0);

				a.setRevers(b);
				b.setRevers(a);

				node[fra].kanter.add(a);
				node[til].kanter.add(b);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
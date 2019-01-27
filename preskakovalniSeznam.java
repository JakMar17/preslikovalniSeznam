public class preskakovalniSeznam {

    private static Seznam prviNivo;

    public static void main (String [] args) {
        prviNivo = new Seznam(0);

        Enota tekoca = prviNivo.prva;

        long start = System.currentTimeMillis();
        for (int i = 1; i < 10000000; i++) {
            tekoca = prviNivo.dodajEnoto(i);
        }
        long konec = System.currentTimeMillis();

        System.out.println(konec-start);
        
        long [] tabela = new long [1000];
        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = iskanje(10000000);
        }

        long vsota = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < tabela.length; i++) {
            vsota += tabela[i];
            if (min > tabela[i])
                min = tabela[i];
            else if (max < tabela[i])
                max = tabela[i];
        }
        System.out.println(vsota/tabela.length);
        System.out.println(min);
        System.out.println(max);

        
        //izpis();
    }

    private static long iskanje (int vrednost) {
        long zacetek = System.currentTimeMillis();
        Enota e = najdiVrednost(prviNivo, vrednost);
        return System.currentTimeMillis()-zacetek;
    }

    private static void izpis() {
        for (Enota e = prviNivo.prva; e != null; e = e.getNaslednja())
            System.out.println(e.getElement());
    }

    private static Enota najdiVrednost (Seznam s, int vrednost) {
        for (Enota e = s.prva; e != null; e = e.getNaslednja())
            if (e.getElement() == vrednost)
                return e;

        return null;
    }
}

class Seznam {
    public Enota prva;
    public Enota zadnja;
    public int stElementov = 0;
    public int nivo;

    public Seznam (int nivo) {
        this.nivo = nivo;
        this.prva = new Enota();
        this.zadnja = this.prva;
    }

    public Seznam (int element, int nivo) {
        this.prva = new Enota (element);
        this.zadnja = this.prva;
        this.stElementov++;
        this.nivo = nivo;
    }

    public Enota dodajEnoto (int element) {
        this.zadnja.setNaslednja(new Enota (element));
        this.zadnja = zadnja.getNaslednja();
        return zadnja;
    }

}

class Enota {
    private int element;
    private Enota naslednja;

    public Enota () {
    }

    public Enota (int element) {
        this.element = element;
    }

    public Enota (int element, Enota naslednja) {
        this.element = element;
        this.naslednja = naslednja;
    }

    public int getElement() {
        return element;
    }
    public Enota getNaslednja() {
        return naslednja;
    }

    public void setElement (int element) {
        this.element = element;
    }

    public void setNaslednja (Enota naslednja) {
        this.naslednja = naslednja;
    }
}
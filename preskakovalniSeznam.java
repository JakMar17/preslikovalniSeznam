public class preskakovalniSeznam {

    private static Seznam prviNivo;
    private static Seznam drugiNivo;

    public static void main (String [] args) {
        prviNivo = new Seznam(0);
        drugiNivo = new Seznam(1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) 
            dodajElement(i);
        System.out.println(System.currentTimeMillis()-start);

        /*izpis(prviNivo);
        izpis(drugiNivo);*/

        System.out.println(prviNivo.stElementov);
        System.out.println(drugiNivo.stElementov);

        long [] tabela1  = new long [10000];
        long [] tabela2 = new long [tabela1.length];

        for (int i = 0; i < tabela1.length; i++) {

            int random = (int) (Math.random()*prviNivo.stElementov);

            start = System.currentTimeMillis();
            iskanjePrimitivno(prviNivo, random);
            tabela1[i] = System.currentTimeMillis()-start;

            start = System.currentTimeMillis();
            iskanje(random);
            tabela2[i] = System.currentTimeMillis()-start;
        }

        long vsota1 = 0;
        for (int i = 0; i < tabela1.length; i++)
            vsota1 += tabela1[i];
        System.out.println("Enonivojsko iskanje " + tabela1.length + " elementov: " + vsota1 + " " + vsota1/tabela1.length);

        long vsota2 = 0;
        for (int i = 0; i < tabela2.length; i++)
            vsota2 += tabela2[i];
        System.out.println("Dvonivojsko iskanje " + tabela2.length + " elementov: " + vsota2 + " " + vsota2/tabela2.length);

        double x = (vsota2*100/vsota1);
        System.out.println("Izboljsano za " + x + "%");
        
    }

    private static void dodajElement (int element) {
        prviNivo.dodajOsnovno(element);
        int random = (int) (Math.random() *2);
        if (random == 1)
            drugiNivo.dodajVisjo(element, prviNivo.zadnja);
    }

    private static void izpis(Seznam s) {
        for (Enota e = s.prva.getNaslednja(); e != null; e = e.getNaslednja())
            System.out.print(e.getElement() + " ");
        System.out.println();
    }

    private static int iskanjePrimitivno (Seznam s, int vrednost) {
        for (Enota e = s.prva; e != null; e = e.getNaslednja())
            if (e.getElement() == vrednost)
                return 1;
        return -1;
    }

    private static int iskanje (int vrednost) {
        for (Enota e = drugiNivo.prva; e.getNaslednja() != null; e = e.getNaslednja()) {
            if (e.getNaslednja().getElement() == vrednost)
                return 1;
            else if (e.getNaslednja().getElement() > vrednost) {
                for (Enota e1 = e.nizjiNivo; e1 != null; e1 = e1.getNaslednja())
                    if (e1.getElement() == vrednost)
                        return 0;
            }
        }
        return -1;
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

    public void dodajOsnovno (int element) {
        this.zadnja.setNaslednja(new Enota(element));
        this.zadnja = zadnja.getNaslednja();
        stElementov++;
    }

    public void dodajVisjo (int element, Enota nizjiNivo) {
        this.zadnja.setNaslednja(new Enota (element, nizjiNivo));
        this.zadnja = zadnja.getNaslednja();
        stElementov++;
    }

}

class Enota {
    private int element;
    private Enota naslednja;
    public Enota nizjiNivo;

    public Enota () {
    }

    public Enota (int element) {
        this.element = element;
    }
    
    public Enota (int element, Enota nizjiNivo) {
        this.element = element;
        this.nizjiNivo = nizjiNivo;
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
public class preskakovalniSeznam {

    private static Seznam prviNivo;
    private static Seznam drugiNivo;
    private static Seznam tretjiNivo;

    public static void main (String [] args) {
        prviNivo = new Seznam(0);
        drugiNivo = new Seznam(1);
        tretjiNivo = new Seznam(2);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) 
            dodajElement(i);
        System.out.println(System.currentTimeMillis()-start);

        System.out.println(prviNivo.stElementov);
        System.out.println(drugiNivo.stElementov);
        System.out.println(tretjiNivo.stElementov);

        /*long [] tabela1  = new long [1000];
        long [] tabela2 = new long [tabela1.length];
        long [] tabela3 = new long [tabela1.length];

        for (int i = 0; i < tabela1.length; i++) {

            int random = (int) (Math.random()*prviNivo.stElementov);

            start = System.currentTimeMillis();
            iskanjePrimitivno(prviNivo, random);
            tabela1[i] = System.currentTimeMillis()-start;

            start = System.currentTimeMillis();
            iskanje(random);
            tabela2[i] = System.currentTimeMillis()-start;

            start = System.currentTimeMillis();
            iskanje3(random);
            tabela3[i] = System.currentTimeMillis()-start;
        }

        long vsota1 = 0;
        for (int i = 0; i < tabela1.length; i++)
            vsota1 += tabela1[i];
        System.out.println("Enonivojsko iskanje " + tabela1.length + " elementov: " + vsota1 + " " + vsota1/tabela1.length);

        long vsota2 = 0;
        for (int i = 0; i < tabela2.length; i++)
            vsota2 += tabela2[i];
        System.out.println("Dvonivojsko iskanje " + tabela2.length + " elementov: " + vsota2 + " " + vsota2/tabela2.length);

        long vsota3 = 0;
        for (int i = 0; i < tabela3.length; i++)
            vsota3 += tabela3[i];
        System.out.println("Tronivojsko iskanje " + tabela3.length + " elementov: " + vsota3 + " " + vsota3/tabela3.length);

        double x = 100-(vsota3*100/vsota2);
        System.out.println("Izboljsano za (3nivojsko/2nivojsko) " + x + "%");

        x = 100-(vsota3*100/vsota1);
        System.out.println("Izboljsano za (3nivojsko/1nivojsko) " + x + "%");*/

        int vsota = 0;
        int [] najden = new int [4];
        for (int i = 0; i < 10000; i++) {
            int random = (int) (Math.random()*prviNivo.stElementov);
            start = System.currentTimeMillis();

            najden[iskanjeRek(random, tretjiNivo.prva, 3)]++;
            vsota += (System.currentTimeMillis() - start);
        }

        System.out.println("Skupen cas " + vsota);
        System.out.println("Povprecen cas " + vsota/10000);

        System.out.println("Najdeni na tretjem nivoju  " + najden[1] + " | " + najden[1]/100 + "%");
        System.out.println("Najdeni na drugem nivoju   " + najden[2] + " | " + najden[2]/100 + "%");
        System.out.println("Najdeni na osnovnem nivoju " + najden[3] + " | " + najden[3]/100 + "%");

        
    }

    private static void dodajElement (int element) {
        prviNivo.dodajOsnovno(element);
        int random = (int) (Math.random() *2);
        if (random == 1) {
            drugiNivo.dodajVisjo(element, prviNivo.zadnja);
            random = (int) (Math.random() *2);
            if (random == 1)
                tretjiNivo.dodajVisjo(element, drugiNivo.zadnja);
        }
    }

    private static void izpis(Seznam s) {
        for (Enota e = s.prva.getNaslednja(); e != null; e = e.getNaslednja())
            System.out.print(e.getElement() + " ");
        System.out.println();
    }

    private static int iskanjeRek (int vrednost, Enota zacetek, int nivo) {
        if (nivo == 1) {
            for(;zacetek != null; zacetek = zacetek.getNaslednja())
                if (zacetek.getElement() == vrednost)
                    return 1;
        }
        else {
            while (zacetek.getNaslednja() != null) {
                if (zacetek.getNaslednja().getElement() == vrednost)
                    return 1;
                else if (zacetek.getNaslednja().getElement() > vrednost)
                    return 1 + iskanjeRek(vrednost, zacetek.nizjiNivo, nivo-1);
                zacetek = zacetek.getNaslednja();
            }
        }
        System.out.println("ni najden");
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
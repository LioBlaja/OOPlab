package lab13;

import java.util.Arrays;
class DuplicateFounded extends Exception{
    public DuplicateFounded(){}
    public DuplicateFounded(String details){
        super(details);
    }
}
class LimitExcedeed extends Exception{
    public LimitExcedeed(){}
    public LimitExcedeed(String details){
        super(details);
    }
}
abstract class Angajat{
    private String nume;
    public double salariu;
    public Angajat(String nume,double salariu){
        this.nume = nume;
        this.salariu = salariu;
    }
    public boolean equals(Object object){
        if(object instanceof Angajat){
            Angajat other = (Angajat)object;
            return (other.nume).equals(this.nume);
        }else {
            return false;
        }
    }
    public abstract double calculSalariu();
    public double getSalariu() {
        return salariu;
    }
    public String toString(){
        return String.format("Nume: %s Salariu: %f\n",this.nume,this.calculSalariu());
    }

    public String getNume() {
        return nume;
    }
}
class AngajatCuSalarFix extends Angajat{
//    private double salariuFix;
    public AngajatCuSalarFix(String numeAngajat,double salariuFix){
        super(numeAngajat,salariuFix);
//        this.salariuFix = salariuFix;
    }
    public double calculSalariu(){
        return this.salariu;
    }
}
class AngajatCuOra extends Angajat{
    private Double[] oreLucrate;
    private int counterZileLucrate;
    private final int nrMaxZile = 31;
    public AngajatCuOra(String nume,double salariuOra){
        super(nume,salariuOra);
        this.oreLucrate = new Double[31];
        this.counterZileLucrate = 0;
    }
    public void adaugaOre(double oreLucrate){//orele lucrate vor fi stocate intern intr-un obiect de acelasi tip
        if(counterZileLucrate < nrMaxZile){
            this.oreLucrate[this.counterZileLucrate] = Double.valueOf(oreLucrate);
            this.counterZileLucrate++;
        }
    }
    public void schimbaSalarPeOra(double salariuNou){
        this.salariu = salariuNou;
    }
    @Override
    public double calculSalariu(){
        double totalOre = 0;
        for (int i = 0; i < this.counterZileLucrate; i++) {
            totalOre += this.oreLucrate[i];
        }
        return totalOre * this.salariu;
    }
}
class Firma{
    private final int maxIntrari = 1024;
    private Angajat[] angajati = new Angajat[this.maxIntrari];
    private int angajatiCounter = 0;
    private boolean findDuplicates(Angajat angajat){
        for (int i = 0; i < this.angajatiCounter; i++) {
//            System.out.println(this.angajati[i].equals(angajat));
            if(this.angajati[i].equals(angajat)){
//                System.out.println(this.angajati[i].equals(angajat));ù
                return false;
            }
        }
        return true;
    }
    public void Angajeaza(Angajat angajat){
        try{
            aux(angajat);
            System.out.printf("Angajatul cu numele %s a fost adaugat\n",angajat.getNume());
        }catch (DuplicateFounded duplicateFounded){
            System.out.println(duplicateFounded.getMessage());
        }catch (LimitExcedeed limitExcedeed){
            System.out.println(limitExcedeed.getMessage());
        }
    }
    private void aux(Angajat angajat) throws  DuplicateFounded,LimitExcedeed{
        if(this.angajatiCounter > 0 && !this.findDuplicates(angajat)){
//            System.out.println(this.findDuplicates(angajat));
//            return -2;
            throw new DuplicateFounded("A fost gasit un duplicat");
        } else if (this.angajatiCounter < this.maxIntrari) {
            this.angajati[this.angajatiCounter] = angajat;
            this.angajatiCounter++;
        } else {
            throw new LimitExcedeed("A fost intrecuta limita");
//            return -1;
        }
    }
    public double salariuMediu(){
        return Arrays.stream(angajati)
                .mapToDouble(Angajat::getSalariu)
                .average()
                .orElse(0.0);
    }
    public String toString(){
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < this.angajatiCounter; i++) {
            out.append(String.format("angajat cu numarul %d:\n%s\n",i+1,this.angajati[i].toString()));
        }
        return out.toString();
    }
}
public class pr36 {
    public static void main(String[] args){
        Firma firma1 = new Firma();

        Angajat angajat1 = new AngajatCuOra("angajatCuOra1",45);
        ((AngajatCuOra)angajat1).adaugaOre(8.5);
        Angajat angajat2 = new AngajatCuSalarFix("angajatCuSalariuFix1",66);
        Angajat angajat3 = new AngajatCuSalarFix("angajatCuSalariuFix1",76);

        firma1.Angajeaza(angajat2);
        firma1.Angajeaza(angajat1);
        System.out.println(firma1.toString());

        firma1.Angajeaza(angajat3);
        System.out.println(firma1.toString());
    }
}

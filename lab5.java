import java.util.Scanner;
import java.util.Random;
class Telefon{
    private String proprietar;
    private String[] proprietari;
    private int proprietariCounter;
    public Telefon(String proprietar){
        this.proprietar = proprietar;
        this.proprietari = new String[100];
        for (int i = 0; i < this.proprietari.length; i++) {
            this.proprietari[i] = "";
        }
        this.proprietariCounter = 0;
    }
    public boolean apeleaza(Telefon telefonApelat){
// in lista proprietarilor telefon apelat bag numele din obiectul de unde am apelat metoda
        if(telefonApelat.getProprietariCounter() < 100){
            telefonApelat.proprietari[telefonApelat.getProprietariCounter()] = this.proprietar;
            telefonApelat.increaseCounter();
            return true;
        }
        return false;
    }

    public Integer numarDeApeluri(String numeProprietar){
        int counter = 0;
        for (String value: this.proprietari) {
            if (value.compareTo(numeProprietar) == 0){
                counter++;
            }
        }
        return Integer.valueOf(counter);
    }

    @Override
    public String toString() {
        StringBuilder proprietari = new StringBuilder();
        proprietari.append("proprietar: " + this.proprietar + " ");
        for (String proprietar: this.proprietari) {
            proprietari.append(proprietar);
        }
        return proprietari.toString();
    }

    public int getProprietariCounter() {
        return this.proprietariCounter;
    }

    public void setProprietar(String proprietar) {
        this.proprietar = proprietar;
    }

    public void increaseCounter(){
        this.proprietariCounter++;
    }
}
class TestCase{
    public void main(){
        Scanner sc = new Scanner(System.in);
        System.out.println("introdu nr de telefoane to be created");
        int nrTelefoane = sc.nextInt();
        sc.nextLine();
        Telefon[] telefoane = new Telefon[nrTelefoane];

        for (int i = 0; i < nrTelefoane; i++) {
            System.out.printf("introduceti numele proprietarului pt telefonul %d:",(i+1));
            String numeProprietar = sc.nextLine();
            telefoane[i] = new Telefon(numeProprietar);
        }

        System.out.println("introduceti numarul de apeluri de efectuat:");
        int A = sc.nextInt();
        Random rd = new Random();

        for (int i = 0; i < A; i++) {
            int x = rd.nextInt(nrTelefoane);
            int y = rd.nextInt(nrTelefoane);
            telefoane[x].apeleaza(telefoane[y]);
        }

        System.out.println("introdu numele proprietarului ce va fi cautat");
        sc.nextLine();
        String numeProprietar = sc.nextLine();
        for (int i = 0; i < nrTelefoane; i++) {
            System.out.println(telefoane[i].toString());
            System.out.println(telefoane[i].numarDeApeluri(numeProprietar));
        }
    }
}
public class lab5 {
    public static void main(String[] args){
        TestCase a = new TestCase();
        a.main();
    }
}

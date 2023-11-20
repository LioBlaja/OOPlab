//ex4 lab7
import java.util.ArrayList;
import java.util.List;
class Member{
    protected int varsta;
    protected String nume;

    public Member(int varsta,String nume){
        this.varsta = varsta;
        this.nume = nume;
    }
}
interface Risky{
    double getRisk();// calculeaza riscuri legate de un proiect
}
abstract class Project implements Risky{
    protected String titlu;
    protected String obiectiv;
    protected List<Long> fonduri;
    protected Member manager;
    protected List<Member> participanti;
    public Project(String titlu,String obiectiv,int varstaManager,String numeManager){
        this.titlu = titlu;
        this.obiectiv = obiectiv;
        this.manager = new Member(varstaManager,numeManager);
        this.fonduri = new ArrayList<>();
        this.participanti = new ArrayList<>();
    }
    public void addFonduri(long fond){
        this.fonduri.add(Long.valueOf(fond));
    }
    public void addMember(Member m) {
        if(this instanceof ProiectMilitar){
            if (this.participanti.size() < ((ProiectMilitar) this).getMaxNumberOfMembers()){
                this.participanti.add(m);
            }else {
                System.out.println("limit reached");
            }
        }else {
            this.participanti.add(m);
        }
    }
    public double getFonduriTotal(){
        int suma = 0;
        for (int i = 0; i < this.fonduri.size(); i++) {
            suma += (this.fonduri.get(i)).floatValue();
        }
        return suma;
    }
    public String toStringProject(){
        StringBuilder output = new StringBuilder();
        output.append("Titlu proiect comercial" + this.titlu + "\n");
        output.append("Obiectiv: " + this.obiectiv + "\n");
        for (int i = 0; i < this.fonduri.size(); i++) {
            output.append("Fond cu numarul " + (i + 1) + ": " + this.fonduri.get(i) + "\n");
        }
        output.append(String.format("Managerul are numele %s si varsta %d \n",this.manager.nume,this.manager.varsta));
        for (int i = 0; i < this.participanti.size(); i++) {
            output.append(String.format("Participant cu numarul %d are numele %s si varsta %d \n",i+1,this.participanti.get(i).nume,this.participanti.get(i).varsta));
        }
        return output.toString();
    }
}
class ProiectComercial extends Project implements Risky{
    private long fonduriMarketing; // jumate din cele de baza , maybe interface
    private int numarEchipe; // mai mic decat nr de membri
    private String deadLine;
    private int maxNumberOfMembers = 15;
    public ProiectComercial(String titlu,String obiectiv,int varstaManager,String numeManager,
                           int numarEchipe,String deadLine){
        super(titlu,obiectiv,varstaManager,numeManager);
        if (numarEchipe < this.participanti.size()){
            this.numarEchipe = numarEchipe;
        }else {
            System.out.println("conditie nerespectata");
        }
        this.deadLine = deadLine;
        this.fonduriMarketing = (long)super.getFonduriTotal() / 2;
    }
    @Override
    public double getRisk() {
        return this.numarEchipe * 3 / this.participanti.size()/ this.getFonduriTotal() - this.fonduriMarketing; //numarulechipelor *3/numarulmembrilor/fonduri- fonduridemarketing
    }
    public String toString(){
        StringBuilder output = new StringBuilder();

        output.append(this.toStringProject());
        output.append(String.format("Fonduri marketing %d\n",this.fonduriMarketing));
        output.append(String.format("Numar echipe %d\n",this.numarEchipe));
        output.append(String.format("Deadline %s",this.deadLine));
        return output.toString();
    }
}
class ProiectOpenSource extends Project implements Risky{
    private String mailingList;
    public ProiectOpenSource(String titlu,String obiectiv,int varstaManager,String numeManager,
                            String mailingList){
        super(titlu,obiectiv,varstaManager,numeManager);
        this.mailingList = mailingList;
    }
    @Override
    public double getRisk() {
        return this.participanti.size() / this.getFonduriTotal();
    }
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append(this.toStringProject());
        out.append(this.mailingList);
        return out.toString();
    }
}
class ProiectMilitar extends Project implements Risky{
    private String password;
    private String deadLine;
    private int maxNumberOfMembers = 15; // not used
    public ProiectMilitar(String titlu,String obiectiv,int varstaManager,String numeManager,
                             String password,String deadLine){
        super(titlu,obiectiv,varstaManager,numeManager);
        this.password = password;
        this.deadLine = deadLine;
    }
    public double getRisk(){
        return this.participanti.size() / this.password.length() / this.getFonduriTotal(); // nr membrilor / lungimea parolei / fonduri;
    }

    public int getMaxNumberOfMembers() {
        return maxNumberOfMembers;
    }
}
class InvestmentCompany{
    private int numarProiecte;
    private List<Project> proiecte;
    public void addProject(Project p){
        this.numarProiecte++;
        this.proiecte.add(p);
    }
    public Project getBadInvestments(){
        double maxValue = this.proiecte.get(1).getRisk();
        int index = 1;
        for (int i = 0; i < this.proiecte.size(); i++) {
            if (this.proiecte.get(i).getRisk() > maxValue){
                maxValue = this.proiecte.get(i).getRisk();
                index = i;
            }
        }
        return this.proiecte.get(index);
    }
    public InvestmentCompany(){
        this.proiecte = new ArrayList<>();
        this.numarProiecte = 0;
    }
    public static void main(String args[]){

        Project proiect1 = new ProiectComercial("proiectComercial1","obiectivProiectComercial1",
        20,"numeManagerProiectComercial1", 10,"deadLineProiectComercial1");
        proiect1.addMember(new Member(20,"participantProiectComercial1"));
        proiect1.addMember(new Member(30,"participantProiectComercial2"));
        proiect1.addFonduri(20200000L);
        System.out.println(proiect1.toString());
        System.out.println("risk: " + proiect1.getRisk());

        InvestmentCompany companie = new InvestmentCompany();
        companie.addProject(proiect1);
//        System.out.println(companie.proiecte.size());

        Project proiect2 = new ProiectOpenSource("proiectOpenSource2","obiectivProiectOpenSource2",
                30,"numeManagerProiectOpenSource2","mailingListOpenSource2");
        proiect2.addMember(new Member(40,"participantProiectOpenSource1"));
        proiect2.addMember(new Member(45,"participantProiectOpenSource2"));
        proiect2.addFonduri(3863783235L);
        System.out.println(proiect2.toString());
        System.out.println(proiect2.getRisk());
        companie.addProject(proiect2);

        Project proiect3 = new ProiectMilitar("proiectMilitar1","obiectivProiectMilitar1",30,
                "managerProiectMilitar1","parolaProiect","deadLineProiectMilitar1");
        proiect3.addMember(new Member(50,"participantProiectMilitar1"));
        proiect3.addMember(new Member(60,"participantProiectMilitar2"));
        proiect3.addFonduri(436543563L);
        companie.addProject(proiect3);

        System.out.println(companie.getBadInvestments().getRisk());
    }
}

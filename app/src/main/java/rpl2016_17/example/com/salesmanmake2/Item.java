package rpl2016_17.example.com.salesmanmake2;

public class Item {
    private String Agenda;
    public Item(String Agenda) {
        this.Agenda = Agenda;
    }

    public Item(){}

    public String getAgenda() {
        return Agenda;
    }

    public void setAgenda(String agenda) {
        Agenda = agenda;
    }
}

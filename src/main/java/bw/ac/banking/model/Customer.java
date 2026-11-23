package bw.ac.banking.model;
public record Customer(long id, String firstName, String surname, String address, boolean employed, String employer, String employerAddress) {
    public Customer { if(firstName==null||firstName.isBlank()||surname==null||surname.isBlank()) throw new IllegalArgumentException("Name required"); }
    public String fullName(){return firstName+" "+surname;}
}

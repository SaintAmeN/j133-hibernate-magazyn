package pl.sda.j133.hibernate.magazyn;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.j133.hibernate.magazyn.model.Produkt;
import pl.sda.j133.hibernate.magazyn.model.Sprzedaz;

/**
 * @author Paweł Recław, AmeN
 * @project j133-hibernate-magazyn
 * @created 03.12.2022
 */
public class ObslugaUsunProdukt implements ObslugaKomendy {

    @Override
    public String getKomenda() {
        return "usun produkt";
    }

    public void obslugaKomendy() {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Podaj id produktu ktorego chcesz usunac:");
            String idString = Main.scanner.nextLine();
            Long produktId = Long.parseLong(idString);

            Produkt produkt = session.get(Produkt.class, produktId);
            if (produkt == null) {
                throw new EntityNotFoundException("Nie znaleziono produktu o id: " + produkt);
            }

            if (produkt.getSprzedaze().isEmpty()){
                session.remove(produkt);
                System.out.println("Udało się usunąć produkt.");
            }else{
                System.err.println("Produkt nie mógł być usunięty ponieważ zawiera powiązane sprzedaże!");
            }

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Błąd: " + e);
        }
    }
}

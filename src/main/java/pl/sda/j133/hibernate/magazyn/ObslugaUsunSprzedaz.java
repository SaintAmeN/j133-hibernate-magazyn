package pl.sda.j133.hibernate.magazyn;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.j133.hibernate.magazyn.model.Produkt;
import pl.sda.j133.hibernate.magazyn.model.Sprzedaz;

import java.util.List;

/**
 * @author Paweł Recław, AmeN
 * @project j133-hibernate-magazyn
 * @created 03.12.2022
 */
public class ObslugaUsunSprzedaz implements ObslugaKomendy {

    @Override
    public String getKomenda() {
        return "usun sprzedaz";
    }

    public void obslugaKomendy() {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Podaj id sprzedazy ktora chcesz usunac:");
            String idString = Main.scanner.nextLine();
            Long sprzedazId = Long.parseLong(idString);

            Sprzedaz sprzedaz = session.get(Sprzedaz.class, sprzedazId);
            if (sprzedaz == null) {
                throw new EntityNotFoundException("Nie znaleziono sprzedazy o id: " + sprzedaz);
            }

            session.remove(sprzedaz);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Błąd: " + e);
        }
    }
}

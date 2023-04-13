import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Diese Klasse implementiert das Interface VerleihService. Es handelt sich
 * lediglich um eine Dummy-Implementation, um die GUI zu testen.
 * 
 * @author GUI-Team
 * @version SoSe 2021
 */
class DummyVerleihService extends AbstractObservableService
        implements VerleihService
{
    // Generator für Zufallszahlen und zufällige boolsche Werte
    private static final Random RANDOM = new Random();

    private static final CD MEDIUM = new CD("Titel", "Kommentar", "Interpret",
            70);
    private static final Kundennummer KUNDENNUMMER = new Kundennummer(123456);
    private static final Kunde ENTLEIHER = new Kunde(KUNDENNUMMER, "Vorname",
            "Nachname");
    private static final Verleihkarte VERLEIHKARTE = new Verleihkarte(ENTLEIHER,
            MEDIUM, Datum.heute());

    /**
     * Erstellt ein neues DummyVerleihService Objekt, welches
     * alle Parameter ignoriert.
     * 
     * @param kundenstamm wird ignoriert
     * @param medienbestand wird ignoriert
     * @param initialBestand wird ignoriert
     */
    public DummyVerleihService(KundenstammService kundenstamm,
            MedienbestandService medienbestand,
            List<Verleihkarte> initialBestand)
    {
    }

    /**
     * Gibt eine neue {@link List} zurück, welche als einziges
     * Element das statische Medium {@link DummyVerleihService#MEDIUM} enthält.
     * 
     * @param kunde wird ignoriert
     * @return Liste
     */
    @Override
    public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
    {
        List<Medium> ergebnisListe = new ArrayList<Medium>();
        ergebnisListe.add(MEDIUM);
        return ergebnisListe;
    }

    /**
     * Entleihkarte unabhängig vom Medium
     * 
     * @param medium wird ignoriert
     * @return {@link DummyVerleihService#ENTLEIHER}
     */
    @Override
    public Kunde getEntleiherFuer(Medium medium)
    {
        return ENTLEIHER;
    }

    /**
     * Verleihkarte unabhängig vom Medium
     * 
     * @param medium wird ignoriert
     * @return {@link DummyVerleihService#VERLEIHKARTE}
     */
    @Override
    public Verleihkarte getVerleihkarteFuer(Medium medium)
    {
        return VERLEIHKARTE;
    }

    /**
     * Gibt eine neue {@link List} zurück, welche als einziges
     * Element die statische {@link DummyVerleihService#VERLEIHKARTE}
     * 
     * @return Liste
     */
    @Override
    public List<Verleihkarte> getVerleihkarten()
    {
        List<Verleihkarte> ergebnisListe = new ArrayList<Verleihkarte>();
        ergebnisListe.add(VERLEIHKARTE);
        return ergebnisListe;
    }

    /**
     * Magic
     * @param medium wird ignoriert
     * @return Random boolean
     */
    @Override
    public boolean istVerliehen(Medium medium)
    {
        return RANDOM.nextBoolean();
    }

    /**
     * Macht nichts
     * @param medien wird ignoriert
     * @param rueckgabeDatum wird ignoriert
     */
    @Override
    public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
    {
    }

    /**
     * Magic
     * @param medien wird ignoriert
     * @return Random boolean
     */
    @Override
    public boolean sindAlleNichtVerliehen(List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    /**
     * Magic
     * @param medien wird ignoriert
     * @return Random boolean
     */
    @Override
    public boolean sindAlleVerliehen(List<Medium> medien)
    {
        return RANDOM.nextBoolean();
    }

    /**
     * Macht nichts
     * @param medien wird ignoriert
     * @param ausleihDatum wird ignoriert
     */
    @Override
    public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
    {
    }

    /**
     * Prüft, ob {@link Kunde} der statische {@link DummyVerleihService#ENTLEIHER} ist
     * 
     * @param kunde Der zu überprüfende Kunde
     * @return Ob es sich um den Kunde handelt
     */
    @Override
    public boolean kundeImBestand(Kunde kunde)
    {
        return ENTLEIHER.equals(kunde);
    }

    /**
     * Prüft, ob {@link Medium} das statische {@link DummyVerleihService#MEDIUM} ist
     * 
     * @param medium das zu übeprüfende Medium
     * @return Ob es sich um das Medium handelt
     */
    @Override
    public boolean mediumImBestand(Medium medium)
    {
        return MEDIUM.equals(medium);
    }

    /**
     * Prüft, ob alle {@link Medium} der {@link Liste} im Bestand sind.
     * Die jeweilige Überprüfung findet über {@link DummyVerleihService#mediumImBestand(Medium)} statt.
     * 
     * @param medien Liste der zu überprüfenden Medien
     * @return Ob alle Medien im Bestand sind.
     */
    @Override
    public boolean medienImBestand(List<Medium> medien)
    {
        boolean result = true;
        for (Medium medium : medien)
        {
            if (!mediumImBestand(medium))
            {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Gibt eine neue {@link List} zurück, welche als einziges
     * Element die statische {@link DummyVerleihService#VERLEIHKARTE}
     * 
     * @param kunde wird ignoriert
     * @return Liste
     */
    @Override
    public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
    {
        List<Verleihkarte> result = new ArrayList<Verleihkarte>();
        result.add(VERLEIHKARTE);
        return result;
    }

    /**
     * Verleihen ist nicht möglich, gibt <i>false</i> zurück.
     * 
     * @param kunde wird ignoriert
     * @param medien wird ignoriert
     * @return false
     */
    @Override
    public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
    {
        return false;
    }
}

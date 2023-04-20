import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger
 * Kommentar.
 * 
 * @author SE2-Team
 * @version SoSe 2021
 */
class VerleihServiceImpl extends AbstractObservableService
        implements VerleihService
{
    /**
     * Diese Map speichert für jedes eingefügte Medium die dazugehörige
     * Verleihkarte. Ein Zugriff auf die Verleihkarte ist dadurch leicht über
     * die Angabe des Mediums möglich. Beispiel: _verleihkarten.get(medium)
     */
    private Map<Medium, Verleihkarte> _verleihkarten;

    /**
     * Der Medienbestand.
     */
    private MedienbestandService _medienbestand;

    /**
     * Der Kundenstamm.
     */
    private KundenstammService _kundenstamm;

    /**
     * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
     * 
     * @param kundenstamm Der KundenstammService.
     * @param medienbestand Der MedienbestandService.
     * @param initialBestand Der initiale Bestand.
     * 
     */
    public VerleihServiceImpl(KundenstammService kundenstamm,
            MedienbestandService medienbestand,
            List<Verleihkarte> initialBestand)
    {
        _verleihkarten = erzeugeVerleihkartenBestand(initialBestand);
        _kundenstamm = kundenstamm;
        _medienbestand = medienbestand;
    }

    @Override
	public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
	{
        /* kundeImBestand(kunde) schaut, ob Kunde überhaupt ausleihen darf
           sindAlleNichtVerliehen(medien) schaut, ob alle Medien verfügbar für Ausleihe sind
           ausleihDatum != null schaut, ob Datum möglich ist für weitere Arbeit
        */
        assert kundeImBestand(kunde) : "Vorbedingung verletzt : kundeImBestand(kunde)";
        assert sindAlleNichtVerliehen(medien) : "Vorbedingung verletzt : sindAlleNichtVerliehen(medien)";
        assert ausleihDatum != null : "Vorbedingung verletzt : ausleihDatum != null";

	    for (Medium medium : medien)
	    {
	        Verleihkarte karte = new Verleihkarte(kunde, medium, ausleihDatum);
            // HIER -> karten werden nicht benutzt
            this._verleihkarten.put(medium, karte);
	
	    }
	
	    informiereUeberAenderung();
	}

	@Override
	public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
	{
        /*
            kundeImBestand(kunde) stellt sicher, dass nur Kunden im Bestand ausleihen dürfen
            medienImBestand(medien) schaut, ob Medien beim Verleihservice sind
         */
        assert kundeImBestand(kunde) : "Vorbedingung verletzt : kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt : medienImBestand(medien)";

	    return sindAlleNichtVerliehen(medien);
	}

	@Override
	public Kunde getEntleiherFuer(Medium medium)
	{
        /*
            istVerliehen(medium) schaut, ob Medium überhaupt verliehen ist
         */

        assert istVerliehen(medium) : "Vorbedingung verletzt : istVerliehen(medium)";

	    Verleihkarte verleihkarte = _verleihkarten.get(medium);
	    return verleihkarte.getEntleiher();
	}

	@Override
	public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
	{

        assert kundeImBestand(kunde) : "Vorbedingung verletzt : kundeImBestand(kunde)";

	    List<Medium> result = new ArrayList<Medium>();
	    for (Verleihkarte verleihkarte : _verleihkarten.values())
	    {
	        if (verleihkarte.getEntleiher()
	            .equals(kunde))
	        {
	            result.add(verleihkarte.getMedium());
	        }
	    }
	    return result;
	}

	@Override
    public List<Verleihkarte> getVerleihkarten()
    {
        return new ArrayList<Verleihkarte>(_verleihkarten.values());
    }

    @Override
	public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
	{

        assert sindAlleVerliehen(medien) : "Vorbedingung verletzt : sindAlleVerliehen(medien)";
        assert rueckgabeDatum != null : "Vorbedingung verletzt : rueckgabeDatum != null";

	    for (Medium medium : medien)
	    {
	        _verleihkarten.remove(medium);
	    }
	    informiereUeberAenderung();
	}

	@Override
    public boolean istVerliehen(Medium medium)
    {
        assert mediumImBestand(medium) : "Vorbedingung verletzt : mediumImBestand(medium)";

        return _verleihkarten.get(medium) != null;
    }

    @Override
    public boolean sindAlleNichtVerliehen(List<Medium> medien)
    {

        assert medienImBestand(medien) : "Vorbedingung verletzt : medienImBestand(medien)";

        boolean quelltextkonventionen = true;

        if (quelltextkonventionen) {

            boolean result = true;
            for (Medium medium : medien) {
                if (istVerliehen(medium)) {
                    result = false;
                }
            }
            return result;

        }
        else {
            for (Medium m : medien) {

                if (istVerliehen(m)) return false;

            }
            return true;
        }

    }

    @Override
    public boolean sindAlleVerliehen(List<Medium> medien)
    {

        assert medienImBestand(medien) : "Vorbedingung verletzt : medienImBestand(medien)";

        boolean result = true;
        for (Medium medium : medien)
        {
            if (!istVerliehen(medium))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean kundeImBestand(Kunde kunde)
    {
        /*
            kunde != schaut, ob mit kunde weiter gearbeitet werden kann
         */
        assert kunde != null : "Vorbedingung verletzt : kunde != null";

        return _kundenstamm.enthaeltKunden(kunde);
    }

    @Override
    public boolean mediumImBestand(Medium medium)
    {

        /*
            medium != null schaut, ob mit medium weitergearbeitet werden kann
         */
        assert medium != null : "Vorbedingung verletzt : medium != null";

        return _medienbestand.enthaeltMedium(medium);
    }

    @Override
    public boolean medienImBestand(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt : medien != null";
        assert !medien.isEmpty() : "Vorbedingung verletzt : !medien.isEmpty()";

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

    @Override
	public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
	{

        assert kundeImBestand(kunde) : "Vorbedingung verletzt : kundeImBestand(kunde)";

	    List<Verleihkarte> result = new ArrayList<Verleihkarte>();
	    for (Verleihkarte verleihkarte : _verleihkarten.values())
	    {
	        if (verleihkarte.getEntleiher()
	            .equals(kunde))
	        {
	            result.add(verleihkarte);
	        }
	    }
	    return result;
	}

	@Override
    public Verleihkarte getVerleihkarteFuer(Medium medium)
    {
        assert istVerliehen(medium) : "Vorbedingung verletzt : istVerliehen(medium)";

        return _verleihkarten.get(medium);
    }

    /**
	 * Erzeugt eine neue HashMap aus dem Initialbestand.
	 */
	private HashMap<Medium, Verleihkarte> erzeugeVerleihkartenBestand(
	        List<Verleihkarte> initialBestand)
	{
	    HashMap<Medium, Verleihkarte> result = new HashMap<Medium, Verleihkarte>();
	    for (Verleihkarte verleihkarte : initialBestand)
	    {
	        result.put(verleihkarte.getMedium(), verleihkarte);
	    }
	    return result;
	}

}

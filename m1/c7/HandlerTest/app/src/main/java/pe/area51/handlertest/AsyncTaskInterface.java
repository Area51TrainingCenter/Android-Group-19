package pe.area51.handlertest;

/**
 * Esta interfaz puede utilizarse para tener muchas implementaciones diferentes
 * de nuestro "AsyncTask". El uso de interfaces sirve para poder ejecutar diferentes
 * objetos llamando a los mismo métodos sin importarnos su implementación. Las clases
 * que deseen implementar la interfaz simplemente deben respetar su contrato.
 */
public interface AsyncTaskInterface {

    public boolean execute();

    public boolean stop();

}

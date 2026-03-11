package fadidev.orbitmines.api.handlers;

/**
 * Created by Fadi on 3-9-2016.
 */
public abstract class Command {

    public abstract String[] getAlias();
    public abstract void dispatch(OMPlayer omp, String[] a);

    public boolean dispatchCancelled(OMPlayer omp, String[] a){
        dispatch(omp, a);

        return true;
    }
}

package shelly.com.gifanimations;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by Thegirlwithspellingmistake on 29/08/16.
 *
 * FireBase Push
 */
public class ContainerHolderSingleton {

    private static ContainerHolder containerHolder;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {
    }

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }
}

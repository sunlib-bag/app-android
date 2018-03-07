package shaolizhi.sunshinebox.ui.index;

/**
 * Created by 邵励治 on 2018/3/8.
 * Perfect Code
 */

public interface NetworkStateHelper {
    boolean getNetworkState();

    void setNetworkChangedListener(NetworkChangedListener networkChangedListener);

    public interface NetworkChangedListener {
        public void networkChanged(boolean networkState);
    }
}

package blockchainvideoapp.com.goviddo.goviddo.coreclass;

public class TransactionRecyclerModel {
    private String mTransaction_amount;
    private String mTransaction_memo;
    private String mTransaction_date;

    public TransactionRecyclerModel(String mTransaction_amount, String mTransaction_memo, String mTransaction_date) {
        this.mTransaction_amount = mTransaction_amount;
        this.mTransaction_memo = mTransaction_memo;
        this.mTransaction_date = mTransaction_date;
    }

    public String getmTransaction_amount() {
        return mTransaction_amount;
    }

    public void setmTransaction_amount(String mTransaction_amount) {
        this.mTransaction_amount = mTransaction_amount;
    }

    public String getmTransaction_memo() {
        return mTransaction_memo;
    }

    public void setmTransaction_memo(String mTransaction_memo) {
        this.mTransaction_memo = mTransaction_memo;
    }

    public String getmTransaction_date() {
        return mTransaction_date;
    }

    public void setmTransaction_date(String mTransaction_date) {
        this.mTransaction_date = mTransaction_date;
    }
}

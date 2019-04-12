package blockchainvideoapp.com.goviddo.goviddo.coreclass;

public class RecentRecyclerModel {

    private String mTextFirst;
    private String mImage;
    private String mtext;

    private int mVideoId;
    private String mVideoName;
    private String mHomeImage;
    private String mShortenText;
    private String mVdoCipherId;

    public int getmVideoId() {
        return mVideoId;
    }

    public void setmVideoId(int mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getmVideoName() {
        return mVideoName;
    }

    public void setmVideoName(String mVideoName) {
        this.mVideoName = mVideoName;
    }

    public String getmHomeImage() {
        return mHomeImage;
    }

    public void setmHomeImage(String mHomeImage) {
        this.mHomeImage = mHomeImage;
    }

    public String getmShortenText() {
        return mShortenText;
    }

    public void setmShortenText(String mShortenText) {
        this.mShortenText = mShortenText;
    }

    public String getmVdoCipherId() {
        return mVdoCipherId;
    }

    public void setmVdoCipherId(String mVdoCipherId) {
        this.mVdoCipherId = mVdoCipherId;
    }

    public String getmVideoDesc() {
        return mVideoDesc;
    }

    public void setmVideoDesc(String mVideoDesc) {
        this.mVideoDesc = mVideoDesc;
    }

    public RecentRecyclerModel(int mVideoId, String mVideoName, String mHomeImage, String mShortenText, String mVdoCipherId, String mVideoDesc) {

        this.mVideoId = mVideoId;
        this.mVideoName = mVideoName;
        this.mHomeImage = mHomeImage;
        this.mShortenText = mShortenText;
        this.mVdoCipherId = mVdoCipherId;
        this.mVideoDesc = mVideoDesc;
    }

    private String mVideoDesc;

    public RecentRecyclerModel(String mImage, String mText) {
        this.mTextFirst = mText;
        this.mImage = mImage;
    }

    public RecentRecyclerModel(String mText) {
        this.mtext = mText;
    }

    public String getmTextFirst() {
        return mTextFirst;
    }

    public String getmImage() {
        return mImage;
    }

    public String getMtext() {
        return mtext;
    }



}

package lt.dualpair.android.data.remote.resource;

import java.io.Serializable;

public class SearchParameters implements Serializable {

    private Boolean searchMale;
    private Boolean searchFemale;
    private Integer minAge;
    private Integer maxAge;

    public Boolean getSearchMale() {
        return searchMale;
    }

    public void setSearchMale(Boolean searchMale) {
        this.searchMale = searchMale;
    }

    public Boolean getSearchFemale() {
        return searchFemale;
    }

    public void setSearchFemale(Boolean searchFemale) {
        this.searchFemale = searchFemale;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}

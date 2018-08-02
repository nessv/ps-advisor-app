package org.fundacionparaguaya.adviserplatform.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * The intermediate representation of the snapshot from the remote database.
 */

public class SnapshotIr {
    @SerializedName("snapshot_economic_id")
    long id;
    @SerializedName("survey_id")
    long surveyId;
    @SerializedName("personal_survey_data")
    Map<String, Object> personalResponses;
    @SerializedName("economic_survey_data")
    Map<String, Object> economicResponses;
    @SerializedName("indicator_survey_data")
    Map<String, String> indicatorResponses;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("user_id")
    long userId;
    @SerializedName("term_cond_id")
    long termCondId;
    @SerializedName("priv_pool_id")
    long privPoolId;
    @SerializedName("organization_id")
    long organizationId;
    @SerializedName("snapshot_indicator_id")
    long snapshotIndicatorId;

    public long getId() {
        return id;
    }

    public long getSnapshotIndicatorId() {
        return snapshotIndicatorId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SnapshotIr rhs = (SnapshotIr) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.surveyId, rhs.surveyId)
                .append(this.personalResponses, rhs.personalResponses)
                .append(this.economicResponses, rhs.economicResponses)
                .append(this.indicatorResponses, rhs.indicatorResponses)
                .append(this.createdAt, rhs.createdAt)
                .append(this.userId, rhs.userId)
                .append(this.termCondId, rhs.termCondId)
                .append(this.privPoolId, rhs.privPoolId)
                .append(this.organizationId, rhs.organizationId)
                .append(this.snapshotIndicatorId, rhs.snapshotIndicatorId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(surveyId)
                .append(personalResponses)
                .append(economicResponses)
                .append(indicatorResponses)
                .append(createdAt)
                .append(userId)
                .append(termCondId)
                .append(privPoolId)
                .append(organizationId)
                .append(snapshotIndicatorId)
                .toHashCode();
    }
}

package com.helios.helios.objects.election;



import java.util.List;

/**
 * Created by kepuss on 16.05.15.
 */


public class Election {
    private String cast_url;
    private String description;
    private String frozen_at;
    private String name;
    private boolean openreg;
    private ElgamalPublicKey public_key;
    private List<Question> questions;
    private String short_name;
    private boolean use_voter_aliases;
    private String uuid;
    private String voters_hash;
    private String voting_ends_at;
    private String voting_starts_at;

    public String getVoting_ends_at() {
        return voting_ends_at;
    }

    public void setVoting_ends_at(String voting_ends_at) {
        this.voting_ends_at = voting_ends_at;
    }

    public String getVoting_starts_at() {
        return voting_starts_at;
    }

    public void setVoting_starts_at(String voting_starts_at) {
        this.voting_starts_at = voting_starts_at;
    }

    public String getCast_url() {
        return cast_url;
    }

    public void setCast_url(String cast_url) {
        this.cast_url = cast_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrozen_at() {
        return frozen_at;
    }

    public void setFrozen_at(String frozen_at) {
        this.frozen_at = frozen_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpenreg() {
        return openreg;
    }

    public void setOpenreg(boolean openreg) {
        this.openreg = openreg;
    }

    public ElgamalPublicKey getPublic_key() {
        return public_key;
    }

    public void setPublic_key(ElgamalPublicKey public_key) {
        this.public_key = public_key;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public boolean isUse_voter_aliases() {
        return use_voter_aliases;
    }

    public void setUse_voter_aliases(boolean use_voter_aliases) {
        this.use_voter_aliases = use_voter_aliases;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVoters_hash() {
        return voters_hash;
    }

    public void setVoters_hash(String voters_hash) {
        this.voters_hash = voters_hash;
    }
}

package no.kh498.util.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Elg
 */
public interface VectorMixIn {

    @JsonIgnore
    int getBlockX();

    @JsonIgnore
    int getBlockY();

    @JsonIgnore
    int getBlockZ();
}

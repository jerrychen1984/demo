package com.demo.example.security.jwt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonSerialize
@ApiModel(description = "换取令牌返回结果")
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    @ApiModelProperty(example = "true", required = true)
    private boolean success;

    @ApiModelProperty(example = "")
    private String error;

    @ApiModelProperty(example = "W#$&YFHFBDJFJU@*132nekefkethekjqk5rhjwhjktrje")
    private String token;

}

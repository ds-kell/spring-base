package vn.com.dsk.demo.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import vn.com.dsk.demo.feature.model.Branch;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private List<AuthorityDto> authorityDtos;
    private Branch branch;
    private String fullName;
    private String address;
    private Date dob;
}

package com.solo.todolist.member.entity;

import com.solo.todolist.exception.BusinessLogicException;
import com.solo.todolist.exception.ExceptionCode;
import com.solo.todolist.item.entity.Item;
import com.solo.todolist.status.entity.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long memberId;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Status> statuses = new ArrayList<>();

    public void changeMemberInfo(Member member) {
        if(member.getPassword() != null) this.password = member.getPassword();
    }

    public void setEncodingPassword(String password) {
        this.password = password;
    }

    public void addItem(Item item) {
        this.items.add(item);
        if(item.getMember() != this) {
            item.setMember(this);
        }
    }

    public void addStatus(Status status) {
        this.statuses.add(status);
        if(status.getMember() != this) {
            status.setMember(this);
        }
    }

    public void changeRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public enum Role {
        ADMIN("ADMIN"),
        USER("USER");
        private final String roleName;

        Role(String roleName) {
            this.roleName = roleName;
        }

        public static void checkRole(String roleName) {
            for (Role role : Role.values()) {
                if (role.roleName.equals(roleName)) {
                    return;
                }
            }
            throw new BusinessLogicException(ExceptionCode.ROLE_CANNOT_CHANGE);
        }
    }
}

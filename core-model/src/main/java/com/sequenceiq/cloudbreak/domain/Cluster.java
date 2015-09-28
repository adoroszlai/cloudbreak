package com.sequenceiq.cloudbreak.domain;

import static com.sequenceiq.cloudbreak.common.type.Status.AVAILABLE;
import static com.sequenceiq.cloudbreak.common.type.Status.CREATE_IN_PROGRESS;
import static com.sequenceiq.cloudbreak.common.type.Status.DELETE_COMPLETED;
import static com.sequenceiq.cloudbreak.common.type.Status.DELETE_IN_PROGRESS;
import static com.sequenceiq.cloudbreak.common.type.Status.REQUESTED;
import static com.sequenceiq.cloudbreak.common.type.Status.START_FAILED;
import static com.sequenceiq.cloudbreak.common.type.Status.START_IN_PROGRESS;
import static com.sequenceiq.cloudbreak.common.type.Status.START_REQUESTED;
import static com.sequenceiq.cloudbreak.common.type.Status.STOPPED;
import static com.sequenceiq.cloudbreak.common.type.Status.STOP_FAILED;
import static com.sequenceiq.cloudbreak.common.type.Status.STOP_IN_PROGRESS;
import static com.sequenceiq.cloudbreak.common.type.Status.STOP_REQUESTED;
import static com.sequenceiq.cloudbreak.common.type.Status.UPDATE_IN_PROGRESS;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sequenceiq.cloudbreak.common.type.Status;

@Entity
@Table(name = "Cluster", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "account", "name" })
})
@NamedQueries({
        @NamedQuery(
                name = "Cluster.findAllClustersByBlueprint",
                query = "SELECT c FROM Cluster c "
                        + "WHERE c.blueprint.id= :id"),
        @NamedQuery(
                name = "Cluster.findOneWithLists",
                query = "SELECT c FROM Cluster c "
                        + "LEFT JOIN FETCH c.hostGroups "
                        + "WHERE c.id= :id"),
        @NamedQuery(
                name = "Cluster.findByStatus",
                query = "SELECT c FROM Cluster c "
                        + "WHERE c.status= :status"
        ),
        @NamedQuery(
                name = "Cluster.findByNameInAccount",
                query = "SELECT c FROM Cluster c "
                        + "WHERE c.name= :name and c.account= :account")
})
public class Cluster implements ProvisionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Stack stack;

    @ManyToOne
    private Blueprint blueprint;

    @Column(nullable = false)
    private String name;

    private String owner;
    private String account;
    @Column(length = 1000000, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long creationStarted;

    private Long creationFinished;

    private Long upSince;

    @Column(length = 1000000, columnDefinition = "TEXT")
    private String statusReason;

    private String ambariIp;

    private String userName;
    private String password;

    private Boolean secure;
    private String kerberosMasterKey;
    private String kerberosAdmin;
    private String kerberosPassword;

    private Boolean emailNeeded;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HostGroup> hostGroups = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AmbariStackDetails ambariStackDetails;

    @ManyToOne
    private FileSystem fileSystem;

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCreationStarted() {
        return creationStarted;
    }

    public void setCreationStarted(Long creationStarted) {
        this.creationStarted = creationStarted;
    }

    public Long getCreationFinished() {
        return creationFinished;
    }

    public void setCreationFinished(Long creationFinished) {
        this.creationFinished = creationFinished;
    }

    public Long getUpSince() {
        return upSince;
    }

    public void setUpSince(Long upSince) {
        this.upSince = upSince;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Boolean getEmailNeeded() {
        return emailNeeded;
    }

    public void setEmailNeeded(Boolean emailNeeded) {
        this.emailNeeded = emailNeeded;
    }

    public Set<HostGroup> getHostGroups() {
        return hostGroups;
    }

    public void setHostGroups(Set<HostGroup> hostGroups) {
        this.hostGroups = hostGroups;
    }

    public boolean isCreateFailed() {
        return status.equals(Status.CREATE_FAILED);
    }

    public boolean isSecure() {
        return secure == null ? false : secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public String getKerberosMasterKey() {
        return kerberosMasterKey;
    }

    public void setKerberosMasterKey(String kerberosMasterKey) {
        this.kerberosMasterKey = kerberosMasterKey;
    }

    public String getKerberosAdmin() {
        return kerberosAdmin;
    }

    public void setKerberosAdmin(String kerberosAdmin) {
        this.kerberosAdmin = kerberosAdmin;
    }

    public String getKerberosPassword() {
        return kerberosPassword;
    }

    public void setKerberosPassword(String kerberosPassword) {
        this.kerberosPassword = kerberosPassword;
    }

    public AmbariStackDetails getAmbariStackDetails() {
        return ambariStackDetails;
    }

    public void setAmbariStackDetails(AmbariStackDetails ambariStackDetails) {
        this.ambariStackDetails = ambariStackDetails;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAmbariIp() {
        return ambariIp;
    }

    public void setAmbariIp(String ambariIp) {
        this.ambariIp = ambariIp;
    }

    public boolean isClusterReadyForStop() {
        return AVAILABLE.equals(status) || STOPPED.equals(status);
    }

    public boolean isAvailable() {
        return AVAILABLE.equals(status);
    }

    public boolean isStopped() {
        return STOPPED.equals(status);
    }

    public boolean isStopFailed() {
        return STOP_FAILED.equals(status);
    }

    public boolean isStartFailed() {
        return START_FAILED.equals(status);
    }

    public boolean isStartRequested() {
        return START_REQUESTED.equals(status);
    }

    public boolean isStopInProgress() {
        return STOP_IN_PROGRESS.equals(status) || STOP_REQUESTED.equals(status);
    }

    public boolean isRequested() {
        return REQUESTED.equals(status);
    }

    public boolean isDeleteInProgress() {
        return DELETE_IN_PROGRESS.equals(status);
    }

    public boolean isDeleteCompleted() {
        return DELETE_COMPLETED.equals(status);
    }

    public boolean isClusterReadyForStart() {
        return STOPPED.equals(status) || START_REQUESTED.equals(status);
    }

    public boolean isModificationInProgress() {
        return CREATE_IN_PROGRESS.equals(status)
                || UPDATE_IN_PROGRESS.equals(status)
                || STOP_IN_PROGRESS.equals(status)
                || START_IN_PROGRESS.equals(status)
                || DELETE_IN_PROGRESS.equals(status);
    }
}

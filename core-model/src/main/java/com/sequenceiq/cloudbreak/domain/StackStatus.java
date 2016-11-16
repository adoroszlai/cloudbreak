package com.sequenceiq.cloudbreak.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sequenceiq.cloudbreak.api.model.DetailedStackStatus;
import com.sequenceiq.cloudbreak.api.model.Status;

@Entity
@Table(name = "stackstatus")
public class StackStatus implements ProvisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "stackstatus_generator")
    @SequenceGenerator(name = "stackstatus_generator", sequenceName = "stackstatus_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stack stack;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private DetailedStackStatus detailedStackStatus;

    public StackStatus() {
    }

    public StackStatus(Stack stack, Status status, DetailedStackStatus detailedStackStatus) {
        this.stack = stack;
        this.status = status;
        this.detailedStackStatus = detailedStackStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDetailedStackStatus(DetailedStackStatus detailedStackStatus) {
        this.detailedStackStatus = detailedStackStatus;
    }

    public Stack getStack() {
        return stack;
    }

    public Status getStatus() {
        return status;
    }

    public DetailedStackStatus getDetailedStackStatus() {
        return detailedStackStatus;
    }
}

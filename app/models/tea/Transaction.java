package models.tea;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.ebean.Model;

@Entity
@MappedSuperclass
public class Transaction extends Model {

	private static final long serialVersionUID = -3291529687044012852L;

	@Id
	public Long transactionNumber;
	
	public Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date registeredDate;
	
    @PrePersist
    protected void onCreate() {
        this.registeredDate = new Date();
    }
}

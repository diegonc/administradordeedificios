import org.hibernate.annotations.Proxy;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Mapeo simple de un objeto factura
 */
@SuppressWarnings("serial")
@Entity @Table( name="factura1" )
@Proxy( lazy=false )
public class FacturaDTO implements Serializable
  {
  private int _id;
  
  private int _version;
  
  private String _nombre;  
  
  ////////////////////////////////////////////////////////////////////////////
  // Getter Persistent Methods
 
  @Id @Column( name="fa_id" ) @GeneratedValue( strategy=IDENTITY )  
  public int getId()
    {
    return _id;
    }

  @Column( name="fa_name" )
  public String getNombre()
    {
    return _nombre;
    }

  @Version @Column( name="fa_version" )
  public int getVersion()
    {
    return _version;
    }
  
  ////////////////////////////////////////////////////////////////////////////
  // Setter Methods   

  public void setId(int id)
    {
    _id = id;
    }

  public void setNombre(String name)
    {
    _nombre = name;
    }

  public void setVersion(int version)
    {
    _version = version;
    }

  }

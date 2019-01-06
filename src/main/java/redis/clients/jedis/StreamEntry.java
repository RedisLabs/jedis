package redis.clients.jedis;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class StreamEntry implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  private StreamnEntryID id;
  private Map<String, String> fields;
  
  public StreamEntry(StreamnEntryID id, Map<String, String> fields) {
    this.id = id;
    this.fields = fields;
  }
  
  public StreamnEntryID getID() {
    return id;
  }
  
  public Map<String, String> getFields() {
    return fields;
  }
  
  @Override
  public String toString() {
    return id + " " + fields;
  }
    
  private void writeObject(java.io.ObjectOutputStream out) throws IOException{
    out.writeUnshared(this.id);
    out.writeUnshared(this.fields);
  }
  
  private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
    this.id = (StreamnEntryID) in.readUnshared();
    this.fields = (Map<String, String>) in.readUnshared();
  }
}

package celiacos.seminarioii.prototipo.google.com.celiapp.photos;


import com.google.firebase.database.DataSnapshot;

public class Photo {
    private String descripcion;
    private String url;
    private String usuario;

    public Photo(DataSnapshot dataSnapshot) {
        descripcion = (String) dataSnapshot.child("descripcion").getValue();
        url = (String) dataSnapshot.child("url").getValue();
        usuario = (String) dataSnapshot.child("usuario").getValue();
    }

    public Photo() {
    }

    public Photo(String titulo, String descripcion, String url, String usuario) {
        this.descripcion = descripcion;
        this.url = url;
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

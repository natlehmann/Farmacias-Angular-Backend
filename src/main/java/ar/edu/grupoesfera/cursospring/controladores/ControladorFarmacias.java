package ar.edu.grupoesfera.cursospring.controladores;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.grupoesfera.cursospring.dao.FarmaciaDao;
import ar.edu.grupoesfera.cursospring.modelo.Farmacia;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
public class ControladorFarmacias {
	
	@Inject
	private FarmaciaDao dao;
	
	@RequestMapping(path="/api/farmacias", method = RequestMethod.GET)
	public List<Farmacia> list() {
		return dao.getAll();
	}
	
	@RequestMapping(path="/api/farmacias/{id}", method = RequestMethod.GET)
	public Farmacia getById(@PathVariable Long id) {
		return dao.getById(id);
	}
	
	
	@RequestMapping(path="/api/farmacias", method = RequestMethod.POST)
	public Farmacia save(Farmacia farmacia) {
		return dao.save(farmacia);
	}
	
	@RequestMapping(path="/api/farmacias/cargar")
	public void cargar() {
		
		FileSystemResource file = new FileSystemResource("/usr/local/tomcat7/webapps/FarmaciasBE/WEB-INF/classes/data.json");

		ObjectMapper mapper = new ObjectMapper();
		try {
			long i = 1;
			
			JsonNode root = mapper.readTree(file.getInputStream());
			Iterator<JsonNode> it = root.get("result").get("records").iterator();
			while (it.hasNext()){
				
				JsonNode nodo = it.next();
				Farmacia farmacia = new Farmacia();
				farmacia.setComuna(nodo.get("Comuna").asText());
				farmacia.setDireccion(nodo.get("Direc_norm").asText());
				farmacia.setGeolocalizacion(nodo.get("WKT").asText());
				farmacia.setId(nodo.get("ID").asLong());
				farmacia.setNombre("Farmacia " + i++);
				farmacia.setTelefono(nodo.get("Telefono").asText());
				farmacia.setBarrio(nodo.get("Barrio").asText());
				dao.save(farmacia);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

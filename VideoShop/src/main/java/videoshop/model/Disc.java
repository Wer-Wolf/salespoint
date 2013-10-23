package videoshop.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.salespointframework.core.catalog.Product;
import org.salespointframework.core.money.Money;
import org.salespointframework.core.quantity.Units;
import org.salespointframework.util.Iterables;

@Entity
public class Disc extends Product {
	
	private String genre;
	private String image;
	
	@OneToMany
	private List<Comment> comments = new LinkedList<Comment>();
	
	@Deprecated
	protected Disc() {}
	
	public Disc(String name, String image, Money price, String genre) {
		super(name, price, Units.METRIC);
		this.image = image;
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public Iterable<Comment> getComments() {
		return Iterables.of(comments);
	}

	public String getImage() {
		return image;
	}
}

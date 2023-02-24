package com.fastcampus.projectboard.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
	@Index(columnList = "title"),
	@Index(columnList = "hashtag"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy"),
})
@Entity
public class Article extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Setter @Column(nullable = false) private String title;	// 제목
	@Setter @Column(nullable = false, length = 10000) private String content;	// 본문

	@Setter private String hashtag;	// 해시태그

	@ToString.Exclude
	@OrderBy("id")
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


	// JPA 엔티티들은 기본 생성자를 가지고 있어야 한다.
	// 평소에는 오픈하지 않을 것이기 때문에 protected로 설정한다.
	protected Article() {}

	private Article(String title, String content, String hashtag) {
		this.title = title;
		this.content = content;
		this.hashtag = hashtag;
	}

	public static Article of(String title, String content, String hashtag) {
		return new Article(title, content, hashtag);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Article article = (Article)o;
		return id != null && id.equals(article.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

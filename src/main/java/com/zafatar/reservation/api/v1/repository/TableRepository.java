package com.zafatar.reservation.api.v1.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Repository;

import com.zafatar.reservation.api.v1.exceptions.TableNotFoundException;
import com.zafatar.reservation.api.v1.model.Table;

/**
 * This is the representation of Table Repository based on Redis Template. This
 * library allows to reach the resources and retrieve or save them properly.
 * 
 * @author zafatar
 *
 */
@Repository
public class TableRepository implements TableRepositoryInterface {
	private static final Logger log = LoggerFactory.getLogger(TableRepository.class);
	private static final String KEY = "tables";    // Redis key prefix for repository
	
	private RedisTemplate<String, String> redisTemplate;
	// The Tables will be kept under a hash;
	// Key / Field => Value
	// Tables / Table:<id> => Table
	private HashOperations<String, String, Table> hashOperations;

	protected TableRepository() {
	} // Visible constructor.

	@Autowired
	private TableRepository(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public void save(Table table) {
		// get sequence here.
		int tableId = this.getNextId();
		table.setId(tableId);
	
		hashOperations.put(KEY, uniqueTableKey(table.getId()), table);
		log.info("Table #" + table.getId() + ": saved.");
	}

	@Override
	public void update(Table table) {
		hashOperations.put(KEY, uniqueTableKey(table.getId()), table);
		log.info("Table #" + table.getId() + ": updated.");
	}

	@Override
	public Table findById(int id) throws TableNotFoundException {
		Table table = (Table) hashOperations.get(KEY, uniqueTableKey(id));
		
		if (table == null)
			throw new TableNotFoundException(id);

		log.info("Table #" + table.getId() + ": found.");
		return table;
	}

	
	@Override
	public Map<String, Table> findAll() {
		// TODO - Add the call.
		return null;
	}

	@Override
	public void delete(Table table) {
		// TODO - Add the call.		
	}

	@Override
	public void deleteAll() {
		// TODO - Add the call.
	}

	/** 
	 * This method returns the next/new id from the sequence for the new table.
	 */
	@Override
	public int getNextId() {
		// TODO: A (distributed) lock mechanism for this critical zone.
		// Lock starts here
		RedisAtomicInteger rai = new RedisAtomicInteger(KEY + ":sequence", this.redisTemplate.getConnectionFactory());
		int nextId = rai.incrementAndGet();
		// Lock end here.
		return nextId;
	}
	
	@Override
	public void resetId() {
		// TODO - Add call for reset all tables and counters.
	}

	/**
	 * This method return the total number of tables in the repository.
	 */
	@Override
	public long count() {
		return hashOperations.size(KEY);
	}
	
	/**
	 * Since this is a redis-based repository, we need a redis key in string
	 * format. We could use casting for Board id to String and keep in this way
	 * too.
	 */
	private String uniqueTableKey(int id) {
		return Table.class.getSimpleName() + ":" + id;
	}
}
package org.apache.accumulo.core.client;

import org.apache.accumulo.core.data.Mutation;

/**
 * Send Mutations to a single Table in Accumulo.
 * 
 * When the user uses a Connector to create a BatchWriter,
 * they specify how much memory and how many threads it should use. 
 * As the user adds mutations to the batch writer, it buffers them. 
 * Once the buffered mutations have used half of the user specified buffer, 
 * the mutations are dumped into the background to be written by a thread pool.
 * If the user specified memory completely fills up, then writes are held.
 * When a user calls flush, it does not return until all buffered mutations are written.
 */
public interface GenericBatchWriter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> {

  /**
   * Queues one mutation to write.
   * 
   * @param m
   *          the mutation to add
   * @throws MutationsRejectedException
   *           this could be thrown because current or previous mutations failed
   */
  
  public void addMutation(Mutation m) throws MutationsRejectedException;
  
  /**
   * Queues several mutations to write.
   * 
   * @param iterable
   *          allows adding any number of mutations iteratively
   * @throws MutationsRejectedException
   *           this could be thrown because current or previous mutations failed
   */
  public void addMutations(Iterable<Mutation> iterable) throws MutationsRejectedException;
  
  /**
   * Send any buffered mutations to Accumulo immediately.
   * 
   * @throws MutationsRejectedException
   *           this could be thrown because current or previous mutations failed
   */
  public void flush() throws MutationsRejectedException;
  
  /**
   * Flush and release any resources.
   * 
   * @throws MutationsRejectedException
   *           this could be thrown because current or previous mutations failed
   */
  public void close() throws MutationsRejectedException;
  
}

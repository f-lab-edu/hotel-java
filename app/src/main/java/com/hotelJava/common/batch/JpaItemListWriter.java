package com.hotelJava.common.batch;

import lombok.Builder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@Builder
/**
 * JpaItemWriter를 리스트로 리턴하기 위한 클래스
 */
public class JpaItemListWriter<T> extends JpaItemWriter<List<T>> {

    private JpaItemWriter<T> jpaItemWriter;

    public JpaItemListWriter(JpaItemWriter<T> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(Chunk<? extends List<T>> items) {
        Chunk<T> totalList = new Chunk();

        for (List<T> list: items) {
            totalList.addAll(list);
        }

        jpaItemWriter.write(totalList);
    }
}



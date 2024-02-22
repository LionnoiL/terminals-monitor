ALTER TABLE terminals ADD COLUMN total_heap_size bigint DEFAULT (0);
ALTER TABLE terminals ADD COLUMN free_heap_size bigint DEFAULT (0);
ALTER TABLE terminals ADD COLUMN used_heap_size bigint DEFAULT (0);
package org.exam.use;

import lombok.Data;

@Data
public class SheetDto {
    private String sheetName;
    private String tableName;
    private String ddl;
}

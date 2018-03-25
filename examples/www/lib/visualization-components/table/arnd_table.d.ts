/**
 * Typing for the table configuration object (to be passed to the table constructor).
 */
interface TableConfiguration<D> {
    /**
     * The DOM SVG element that will contain the table.
     */
    container: HTMLElement;
    /**
     * The data to be shown in the table.
     */
    data: string | D[];
}
/**
 * An interactive D3.js component to render objects in a table.
 */
declare class Table<D> {
    private config;
    private data;
    private selection;
    constructor();
    /**
     * Builds the buble tree diagram as specified by the given configuration.
     *
     * @param {BubbleTreeConfiguration} config - the configuration
     */
    build(config: TableConfiguration<D>): void;
}

/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace com.amaris.javatsinterop.dto {
    /**
     * This DTO represents a generic tree node. It can be used as an object to
     * transfer tree structures in REST APIs (typically, to be serialized in JSON).
     * 
     * @author Renaud Pawlak
     * @param {number} test
     * @param {string} name
     * @param {number} weight
     * @class
     */
    export interface GenericNode {
        name : string;

        weight : number;

        children : GenericNode[];

        test : number;

        constructor(test? : any, name? : any, children? : any);
    }
}


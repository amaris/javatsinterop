// This file was generated automatically with JavaTsInterop stub generator
namespace com.amaris.javatsinterop.api {
  export class TestResource {

    constructor(private baseUrl : string = '') {}

    private _xhr(method: string, url: string, callback: any): void {
      var xhr = new XMLHttpRequest();
      xhr.open(method, url, true);
      xhr.setRequestHeader('Content-type', 'application/json');
      xhr.onload = () => { callback(JSON.parse(xhr.responseText)); };
      xhr.send();
    }

    helloWorld(callback : (data : string) => void) : void {
      this._xhr('GET', this.baseUrl + 'test/hello', callback);
    }

    tree(callback : (data : com.amaris.javatsinterop.dto.GenericNode) => void) : void {
      this._xhr('GET', this.baseUrl + 'test/tree', callback);
    }

  }

}

// This file was generated automatically with JavaTsInterop stub generator
namespace com.amaris.javatsinterop.api {
  export class ExampleResource {

    constructor(private baseUrl : string = '') {}

    private _xhr(method: string, url: string, callback: any): void {
      var xhr = new XMLHttpRequest();
      xhr.open(method, url, true);
      xhr.setRequestHeader('Content-type', 'application/json');
      xhr.onload = () => { callback(JSON.parse(xhr.responseText)); };
      xhr.send();
    }

    tree(callback : (data : com.amaris.javatsinterop.dto.GenericNode) => void) : void {
      this._xhr('GET', this.baseUrl + 'example/tree', callback);
    }

    salaries(rank, discipline, callback : (data : com.amaris.javatsinterop.dto.Salary[]) => void) : void {
      let url = this.baseUrl + 'example/salaries';
      url += '?rank='+(rank==null?'':encodeURIComponent(rank))+'&discipline='+(discipline==null?'':encodeURIComponent(discipline));
      this._xhr('GET', url, callback);
    }

  }

}


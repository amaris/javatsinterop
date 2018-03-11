namespace com.amaris.javatsinterop.api {
    export class TestResource {

        private _xhr(method: string, url: string, callback: any): void {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url, true);
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.onload = () => {
                callback(JSON.parse(xhr.responseText));
            };
            xhr.send();
        }

        constructor(private baseUrl: string) { }

        helloWorld(callback: (data: string) => void): void {
            this._xhr("GET", this.baseUrl + "home/hello", callback);
        }

        tree(callback: (data: com.amaris.javatsinterop.dto.GenericNode) => void): void {
            this._xhr("GET", this.baseUrl + "home/tree", callback);
        }

    }

}


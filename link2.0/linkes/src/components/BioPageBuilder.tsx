import { useState } from 'react';
import { Plus, Trash2, ExternalLink, Eye, Copy } from 'lucide-react';
import { toast } from 'sonner';

interface BioLink {
  id: string;
  title: string;
  url: string;
}

export function BioPageBuilder() {
  const [profileName, setProfileName] = useState('');
  const [bio, setBio] = useState('');
  const [links, setLinks] = useState<BioLink[]>([]);
  const [newLinkTitle, setNewLinkTitle] = useState('');
  const [newLinkUrl, setNewLinkUrl] = useState('');
  const [showPreview, setShowPreview] = useState(false);

  const addLink = () => {
    if (!newLinkTitle || !newLinkUrl) {
      toast.error('Preencha título e URL do link');
      return;
    }

    const newLink: BioLink = {
      id: Date.now().toString(),
      title: newLinkTitle,
      url: newLinkUrl
    };

    setLinks([...links, newLink]);
    setNewLinkTitle('');
    setNewLinkUrl('');
    toast.success('Link adicionado!');
  };

  const removeLink = (id: string) => {
    setLinks(links.filter(link => link.id !== id));
    toast.success('Link removido');
  };

  const copyBioPageUrl = () => {
    const url = `https://seusite.com/${profileName.toLowerCase().replace(/\s+/g, '-')}`;
    navigator.clipboard.writeText(url);
    toast.success('URL copiada!');
  };

  return (
    <div className="grid lg:grid-cols-2 gap-8">
      {/* Editor */}
      <div className="bg-white rounded-2xl p-8 shadow-xl border border-gray-100">
        <h3 className="mb-6 text-gray-900">
          ✨ Crie sua Bio Page
        </h3>

        <div className="space-y-6">
          <div>
            <label className="block mb-2 text-gray-700">Nome/Título</label>
            <input
              type="text"
              value={profileName}
              onChange={(e) => setProfileName(e.target.value)}
              placeholder="Seu nome ou marca"
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all"
            />
          </div>

          <div>
            <label className="block mb-2 text-gray-700">Descrição</label>
            <textarea
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              placeholder="Conte um pouco sobre você..."
              rows={3}
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all resize-none"
            />
          </div>

          <div className="border-t pt-6">
            <h4 className="mb-4 text-gray-800">Adicionar Links</h4>
            
            <div className="space-y-3 mb-4">
              <input
                type="text"
                value={newLinkTitle}
                onChange={(e) => setNewLinkTitle(e.target.value)}
                placeholder="Título do link"
                className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all"
              />
              <input
                type="url"
                value={newLinkUrl}
                onChange={(e) => setNewLinkUrl(e.target.value)}
                placeholder="https://..."
                className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all"
              />
              <button
                onClick={addLink}
                className="w-full px-6 py-3 bg-gradient-to-r from-indigo-600 to-purple-600 text-white rounded-xl hover:shadow-lg transition-all flex items-center justify-center gap-2"
              >
                <Plus className="w-5 h-5" />
                Adicionar Link
              </button>
            </div>

            {/* Links List */}
            <div className="space-y-2">
              {links.map((link) => (
                <div
                  key={link.id}
                  className="flex items-center gap-3 p-3 bg-gray-50 rounded-lg group hover:bg-gray-100 transition-colors"
                >
                  <div className="flex-1">
                    <div className="text-gray-900">{link.title}</div>
                    <div className="text-sm text-gray-500 truncate">{link.url}</div>
                  </div>
                  <button
                    onClick={() => removeLink(link.id)}
                    className="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors opacity-0 group-hover:opacity-100"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>
              ))}
            </div>
          </div>

          <button
            onClick={() => setShowPreview(!showPreview)}
            className="w-full px-6 py-3 bg-white border-2 border-purple-600 text-purple-600 rounded-xl hover:bg-purple-50 transition-all flex items-center justify-center gap-2"
          >
            <Eye className="w-5 h-5" />
            {showPreview ? 'Ocultar' : 'Ver'} Preview
          </button>
        </div>
      </div>

      {/* Preview */}
      <div className="bg-gradient-to-br from-purple-100 to-pink-100 rounded-2xl p-8 shadow-xl">
        <div className="flex items-center justify-between mb-6">
          <h3 className="text-gray-900">Preview da Bio Page</h3>
          {profileName && (
            <button
              onClick={copyBioPageUrl}
              className="flex items-center gap-2 px-4 py-2 bg-white rounded-lg hover:shadow-md transition-all text-sm text-gray-700"
            >
              <Copy className="w-4 h-4" />
              Copiar URL
            </button>
          )}
        </div>

        <div className="bg-white rounded-2xl p-8 min-h-[500px]">
          {profileName || bio || links.length > 0 ? (
            <div className="text-center">
              {/* Profile Avatar Placeholder */}
              <div className="w-24 h-24 bg-gradient-to-br from-indigo-500 to-purple-500 rounded-full mx-auto mb-4 flex items-center justify-center text-white text-2xl">
                {profileName ? profileName[0].toUpperCase() : '?'}
              </div>

              {profileName && (
                <h2 className="mb-2 text-gray-900">{profileName}</h2>
              )}

              {bio && (
                <p className="text-gray-600 mb-8">{bio}</p>
              )}

              {/* Links */}
              <div className="space-y-3">
                {links.map((link) => (
                  <a
                    key={link.id}
                    href={link.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="block px-6 py-4 bg-gradient-to-r from-indigo-500 to-purple-500 text-white rounded-xl hover:shadow-lg hover:scale-102 transition-all group"
                  >
                    <div className="flex items-center justify-between">
                      <span>{link.title}</span>
                      <ExternalLink className="w-5 h-5 opacity-0 group-hover:opacity-100 transition-opacity" />
                    </div>
                  </a>
                ))}
              </div>
            </div>
          ) : (
            <div className="flex items-center justify-center h-full text-gray-400">
              <div className="text-center">
                <div className="text-6xl mb-4">🎨</div>
                <p>Comece a criar sua bio page</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
